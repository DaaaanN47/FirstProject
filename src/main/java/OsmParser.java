import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OsmParser {

    private static final String TAG_HIGHWAY = "highway";

    public Graph graph;

    private static final Set<String> roadTypes = Stream.of("trunk", "primary","secondary",
            "tertiary", "unclassified", "residential", "motorway_link","trunk_link","primary_link",
            "secondary_link","tertiary_link","living_street","service","track","road").collect(Collectors.toSet());

    public  Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        graph = new Graph();
        //String dir = System.getProperty("user.dir") + "\\src\\NAB-CH.osm";
        ///home/kochnev_a/projects/untitled/src/NAB-CH.osm
        return builder.parse("/home/kochnev_a/projects/untitled/src/NAB-CH.osm");
    }

    public void CheckWayParams(Node way){
        NamedNodeMap attributes = way.getAttributes();
        NodeList tagList = way.getChildNodes();
        int tagListLength = tagList.getLength()-1;
        for (int j=tagListLength; j>0; j--) {
            Node refNode = tagList.item(j);
            if (tagList.item(j).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            NamedNodeMap refAttributes = refNode.getAttributes();
            if (refNode.getNodeName().equals("tag")) {
                String keyStr = refAttributes.getNamedItem("k").getNodeValue();
                if (keyStr.equals(TAG_HIGHWAY)) {
                    String valStr = refAttributes.getNamedItem("v").getNodeValue();
                    boolean isAdded = roadTypes.add(valStr);
                    if(!isAdded){
                        GetWay(attributes, tagList);
                    }
                    else{
                        roadTypes.remove(valStr);
                    }
                    break;
                }
                continue;
            }
        }
    }
    private void GetWay(NamedNodeMap atributes, NodeList tagList){
        WayOSM wayOsm = new WayOSM(Long.parseLong(atributes.getNamedItem("id").getNodeValue()));
        int tagListLength = tagList.getLength();
        for(int i =0 ; i<tagListLength; i++) {
            Node refNode = tagList.item(i);
            NamedNodeMap refAttributes = refNode.getAttributes();
            if (tagList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if(refNode.getNodeName().equals("nd")){
                String ref =  refAttributes.getNamedItem("ref").getNodeValue();
                graph.getAllNodesIds().add(Long.parseLong(ref));
                wayOsm.refs.add(Long.parseLong(ref));
            }
            else{
                graph.wayMap.put(wayOsm.getId(), wayOsm);
                break;
            }
        }
    }
    public void getAllNodeObjects(NodeList nodeList){
        int nodeListLength = nodeList.getLength();
        for (int i = 0; i < nodeListLength; i++) {
            Node node = nodeList.item(i);
            if(node.getNodeName().equals("node")){
                NamedNodeMap attributes = node.getAttributes();
                long nodeId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());
                boolean isContain = graph.getAllNodesIds().contains(nodeId);
                if (isContain) {
                    if (graph.nodeMap.containsKey(nodeId)) {
                        graph.nodeMap.get(nodeId).setIsCrossRoad();
                    } else {
                        NodeOSM nodeOSM = new NodeOSM(nodeId,
                                Double.parseDouble(attributes.getNamedItem("lat").getNodeValue()),
                                Double.parseDouble(attributes.getNamedItem("lon").getNodeValue()));
                        graph.nodeMap.put(nodeOSM.getId(), nodeOSM);
//                        graph.getWayMap().entrySet().forEach(e->{
//                            if (e.getValue().refs.get(0) == nodeId || e.getValue().refs.get(e.getValue().refs.size() - 1) == nodeId) {
//                                graph.nodeMap.get(nodeId).setIsCrossRoad();
//                            }
//                        });
                    }
                }
            }
        }
        setCrossRoad();
    }
    public void GetNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        long nodeId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());

        graph.wayMap.forEach((key, value) -> {
            long wayId = key;
            boolean isContain = value.refs.contains(nodeId);
            if (isContain) {
                if (graph.nodeMap.containsKey(nodeId)) {
                    graph.nodeMap.get(nodeId).setIsCrossRoad();
                    //graph.nodeMap.get(nodeId).waysHasNode.add(wayId);
                } else {
                    NodeOSM nodeOSM = new NodeOSM(nodeId,
                            Double.parseDouble(attributes.getNamedItem("lat").getNodeValue()),
                            Double.parseDouble(attributes.getNamedItem("lon").getNodeValue()));
                    nodeOSM.waysHasNode.add(key);
                    graph.nodeMap.put(nodeOSM.getId(), nodeOSM);
                    if (value.refs.get(0) == nodeId || value.refs.get(value.refs.size() - 1) == nodeId) {
                            graph.nodeMap.get(nodeId).setIsCrossRoad();
                    }
                }
            }
        });
    }
    private void setCrossRoad(){
        graph.wayMap.forEach((key, value) ->{
            long startIndex = value.refs.get(0);
            long finishIndex = value.refs.get(value.refs.size() - 1);
            if (graph.nodeMap.containsKey(startIndex)){
                graph.nodeMap.get(startIndex).setIsCrossRoad();
            }
            if(graph.nodeMap.containsKey(finishIndex)) {
                graph.nodeMap.get(finishIndex).setIsCrossRoad();
            }
        });
    }

}
//