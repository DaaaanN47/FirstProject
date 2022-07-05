import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

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
                CoordinatesTree(4, 55.6874224,52.3233298, 55.7774766,52.429723);
        OsmParser osmParser = new OsmParser();
        Document document = osmParser.getDocument();

        Graph graph = osmParser.graph;
        System.out.println(System.currentTimeMillis());

        Node node = document.getFirstChild();
        NodeList nodeList = node.getChildNodes();

        int nodeListLength = nodeList.getLength();
        for (int i = 0; i < nodeListLength; i++) {
            if(!nodeList.item(i).getNodeName().equals("way")){
                continue;
            } else if (nodeList.item(i).getNodeName().equals("relation")) {
                break;
            }
            osmParser.CheckWayParams(nodeList.item(i));
        }
        System.out.println(System.currentTimeMillis() + " ways done");
        osmParser.getAllNodeObjects(nodeList);
//        for (int i = 0; i < nodeListLength; i++) {
//            if(nodeList.item(i).getNodeName().equals("node")){
//                osmParser.GetNode(nodeList.item(i));
//            } else if (nodeList.item(i).getNodeName().equals("way")) {
//                break;
//            }
//        }

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
        Vertex start = new Vertex( 55.7530261,52.4101473);
        Vertex finish = new Vertex( 55.7450844,52.3967016);
        Set<Long> nearestVertexes = root.getNearestVertexes(start);
        Set<Long> nearestVertexes1 = root.getNearestVertexes(finish);
        if(nearestVertexes.size()==0 || nearestVertexes1.size()==0){
            System.out.println("Точка вне рассматриваемой зоны или произошла ошибка");
        }

        HashMap<Long,Vertex> nearStartvertex = new HashMap<>();
        nearestVertexes.stream().forEach(e->{
            nearStartvertex.put(e,graph.vertexMap.get(e));
        });
        HashMap<Long,Vertex> nearFinishtvertex = new HashMap<>();
        nearestVertexes1.stream().forEach(e->{
            nearFinishtvertex.put(e,graph.vertexMap.get(e));
        });
        Vertex startVertex  = graph.getClosestVertex(nearStartvertex,start);
        Vertex finVertex  = graph.getClosestVertex(nearFinishtvertex,finish);

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        dijkstraAlgorithm.setInfDistToVertexes(graph,startVertex);
        graph.vertexMap.entrySet().forEach(e->{
            if(e.getValue().getId()==510188427){
                Vertex test1 = graph.vertexMap.get(e.getValue().getId());
                System.out.println(test1.getLat() + "  " + test1.getLon());
            }
            if(e.getValue().getId()==571103275){
                Vertex test1 = graph.vertexMap.get(e.getValue().getId());
                System.out.println(test1.getLat() + "  " + test1.getLon());
            }
            if(e.getValue().getId()==2075688508){
                Vertex test1 = graph.vertexMap.get(e.getValue().getId());
                System.out.println(test1.getLat() + "  " + test1.getLon());
            }
        });
        dijkstraAlgorithm.CheckVertexes(graph,startVertex,finVertex);
       ArrayList<Vertex> path = (ArrayList<Vertex>) dijkstraAlgorithm.getVertexPath(finVertex);
        System.out.println(System.currentTimeMillis());
        System.out.println("Madina privet");
    }
}
