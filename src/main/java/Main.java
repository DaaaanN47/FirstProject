import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

//
public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
//        System.out.println("Широта верхней правой точки: ");
//        double topRightLat  = System.in.read();
//        System.out.println("Долгота верхней правой точки: ");
//        double topRightLon  = System.in.read();
//        System.out.println("Долгота нижней левой точки: ");
//        double botLeftLat  = System.in.read();
//        System.out.println("Долгота нижней левой точки: ");
//        double botLeftLon  = System.in.read();
//        System.out.println("Количесво вложений: ");
//        double lon  = System.in.read();
//        CoordinatesTree root = new CoordinatesTree(4,topRightLat,topRightLon,botLeftLat,botLeftLon);
        CoordinatesTree root = new
                CoordinatesTree(5, 55.6874224,52.3233298, 55.7774766,52.429723);
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
        graph.fillVertexAndEdgesMap();

        graph.getVertexesFromEdges(root);
        System.out.println(System.currentTimeMillis() + " vertex done" );

        graph.getEdgeWeights();
        System.out.println(System.currentTimeMillis() + " weights done" );

        graph.ConvertEdgeSetIntoHashMap();
        //DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
//        Scanner in = new Scanner(System.in);
//        System.out.println("Широта стартовой точки: ");
//        double startLat  = in.nextDouble();
//        System.out.println("Долгота стартовой точки: ");
//        double startLon  = in.nextDouble();
        Vertex start = new Vertex( 55.7567540,52.4142762);
        Set<Long> nearestvertexes = root.getNearestVertexes(start);
        if(nearestvertexes==null){
            System.out.println("Точка вне рассматриваемой зоны");
        }
        Map<Long,Vertex> nearvertex = new HashMap<>();
        nearestvertexes.stream().forEach(e->{
            nearvertex.put(e,graph.vertexMap.get(e));
        });

        System.out.println("Madina privet");
    }
}
