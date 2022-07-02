import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
//
public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        CoordinatesTree root = new CoordinatesTree(1);
        OsmParser osmParser = new OsmParser();
        Document document = osmParser.getDocument();
        Graph graph = osmParser.graph;
        System.out.println(System.currentTimeMillis());
        Node node = document.getFirstChild();
        NodeList nodeList = node.getChildNodes();
        int nodeListLenngth = nodeList.getLength();
        for (int i = 0; i < nodeListLenngth; i++) {
            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if(!nodeList.item(i).getNodeName().equals("way")){
                continue;
            } else if (nodeList.item(i).getNodeName().equals("relation")) {
                break;
            }
            osmParser.CheckWayParams(nodeList.item(i));
        }
        System.out.println("ways done");
        for (int i = 0; i < nodeListLenngth; i++) {
            if (nodeList.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if(nodeList.item(i).getNodeName().equals("node")){
                osmParser.GetNode(nodeList.item(i));
            } else if (nodeList.item(i).getNodeName().equals("way")) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() + " nodes done" );
        graph.wayMap.entrySet().stream().forEach(e->{
            graph.getEdgesFromWay(e.getValue());
        });
        System.out.println(System.currentTimeMillis() + " edges done" );
        graph.getVertexesFromEdges(root);
        graph.fillVertexMap();
        System.out.println(System.currentTimeMillis() + " vertex done" );
        graph.getEdgeWeights();
        System.out.println(System.currentTimeMillis() + " weights done" );
        graph.ConvertEdgeSetIntoHashMap();
        //DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();

        System.out.println("Madina privet");


    }
}
