import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OsmParser {

    private static final String TAG_HIGHWAY = "highway";

    public Graph graph;

    private int counter;

    Map<String, Integer> roadAndSpeed;

    private void fillRoadSpeedMap() throws IOException {
        //E:\MaksimProject\src\main\resources\config.properties
        ///home/kochnev_a/projects/untitled/src/main/resources/config.properties
        FileInputStream fileInputStream = new FileInputStream("E:\\MaksimProject\\src\\main\\resources\\config.properties");
        Properties properties = new Properties();
        properties.load(fileInputStream);
        roadAndSpeed = new HashMap<>();
        roadTypes.forEach(roadType->{
            int speed = Integer.valueOf(properties.getProperty(roadType));
            roadAndSpeed.put(roadType, speed);
        });
    }


    private static final Set<String> roadTypes = Stream.of("trunk","motorway", "primary", "secondary",
            "tertiary", "unclassified", "residential", "motorway_link", "trunk_link", "primary_link",
            "secondary_link", "tertiary_link", "living_street", "service", "track", "road").collect(Collectors.toSet());

    public  Document getDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        graph = new Graph();
        String dir = System.getProperty("user.dir") + "\\src\\NAB-CH.osm";
        ///home/kochnev_a/projects/untitled/src/NAB-CH.osm
        return builder.parse(dir);
    }
    public void CheckWays(NodeList nodeList, boolean fillSpeedMap) throws IOException {

        int nodeListLength = nodeList.getLength();
        for (int i = 0; i < nodeListLength; i++) {
            if(!nodeList.item(i).getNodeName().equals("way")){
                continue;
            } else if (nodeList.item(i).getNodeName().equals("relation")) {
                break;
            }
            CheckWayParams(nodeList.item(i));
        }
        if(!fillSpeedMap){
            fillRoadSpeedMap();
            setRoadsMaxSpeed();
        }

    }
    private void CheckWayParams(Node way){
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
                        GetWay(attributes, tagList, valStr);
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
    private void GetWay(NamedNodeMap atributes, NodeList tagList, String roadType){
        WayOSM wayOsm = new WayOSM(Long.parseLong(atributes.getNamedItem("id").getNodeValue()));
        wayOsm.setRoadType(roadType);
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
                wayOsm.getRefs().add(Long.parseLong(ref));
            }
            else if(refNode.getNodeName().equals("tag")){
                String keyStr = refAttributes.getNamedItem("k").getNodeValue();
                if(keyStr.equals("maxspeed")){
                    String valStr = refAttributes.getNamedItem("v").getNodeValue();
                    wayOsm.setMaxSpeed(Integer.parseInt(valStr));
                    graph.getWayMap().put(wayOsm.getId(), wayOsm);
                    break;
                }
            }
        }
        graph.getWayMap().put(wayOsm.getId(), wayOsm);
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
                        NodeOSM nodeOSM = new NodeOSM(nodeId,
                                Double.parseDouble(attributes.getNamedItem("lat").getNodeValue()),
                                Double.parseDouble(attributes.getNamedItem("lon").getNodeValue()));
                        graph.getNodeMap().put(nodeOSM.getId(), nodeOSM);
                }
            }
        }
        setCrossRoad();
        checkRepeatNodes();
    }
    private void setCrossRoad(){
        graph.getWayMap().forEach((key, value) ->{
            long startIndex = value.getRefs().get(0);
            long finishIndex = value.getRefs().get(value.getRefs().size() - 1);
            if (graph.getNodeMap().containsKey(startIndex)){
                graph.getNodeMap().get(startIndex).setIsCrossRoad();
            }
            if(graph.getNodeMap().containsKey(finishIndex)) {
                graph.getNodeMap().get(finishIndex).setIsCrossRoad();
            }
        });
    }
    private void checkRepeatNodes(){
        graph.getAllNodesIds().forEach(nodeId->{
            long currentId= nodeId;
            counter = 0;
            for (WayOSM wayOSM: graph.getWayMap().values()){
                if(wayOSM.getRefs().contains(nodeId)){
                    counter++;
                }
                if(counter>1){
                    graph.getNodeMap().get(currentId).setIsCrossRoad();
                    break;
                }
            }
        });
    }
    private void setRoadsMaxSpeed(){
        graph.getWayMap().entrySet().forEach(way->{
            if(way.getValue().getMaxSpeed()==0){
                way.getValue().setMaxSpeed(roadAndSpeed.get(way.getValue().getRoadType()));
            }
        });
    }

}
//