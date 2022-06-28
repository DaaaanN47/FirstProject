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
        //E:\MaksimProject\FirstProject-master\src\NAB-CH.osm
        return builder.parse(new File("E:\\MaksimProject\\FirstProject-master\\src\\NAB-CH.osm"));
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
                wayOsm.refs.add(Long.parseLong(ref));
            }
            else{
                graph.wayMap.put(wayOsm.getId(), wayOsm);
                break;
            }
        }
    }
    
    public void GetNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        long nodeId = Long.parseLong(attributes.getNamedItem("id").getNodeValue());

        graph.wayMap.entrySet().forEach(entry-> {
            long wayId = entry.getKey();
            boolean isContain = entry.getValue().refs.contains(nodeId);
            if (isContain) {
                if (graph.nodeMap.containsKey(nodeId)){
                        graph.nodeMap.get(nodeId).setIsCrossRoad(true, wayId);
                        //graph.nodeMap.get(nodeId).waysHasNode.add(wayId);
                }
                else{
                        NodeOSM nodeOSM = new NodeOSM(nodeId);
                        nodeOSM.setLat(Double.parseDouble(attributes.getNamedItem("lat").getNodeValue()));
                        nodeOSM.setLon(Double.parseDouble(attributes.getNamedItem("lon").getNodeValue()));
                        nodeOSM.waysHasNode.add(entry.getKey());
                        graph.nodeMap.put(nodeOSM.getId(), nodeOSM);
                    if (entry.getValue().refs.get(0) == nodeId || entry.getValue().refs.get(entry.getValue().refs.size() - 1) == nodeId) {
                        try {

                            graph.nodeMap.get(nodeId).setIsCrossRoad(true, wayId);

                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }

                }
            }
        });
    }

}

//NodeOSM nodeOSM = new NodeOSM(id);
//                        nodeOSM.setLat(Double.parseDouble(attributes.getNamedItem("lat").getNodeValue()));
//                                nodeOSM.setLon(Double.parseDouble(attributes.getNamedItem("lon").getNodeValue()));
//                                graph.nodeMap.put(nodeOSM.getId(), nodeOSM);