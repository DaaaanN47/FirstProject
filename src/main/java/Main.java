import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

//
public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        //CoordinatesTree root = new CoordinatesTree(4, 55.6874224,52.3233298, 55.7774766,52.429723);
        OsmParser osmParser = new OsmParser();
        Document document = osmParser.getDocument();

        Graph graph = osmParser.graph;
        System.out.println(System.currentTimeMillis());

        Node node = document.getFirstChild();
        NodeList nodeList = node.getChildNodes();

        osmParser.CheckWays(nodeList);
        CoordinatesTree root = new CoordinatesTree(nodeList,4);

        System.out.println(System.currentTimeMillis() + " ways done");
        osmParser.getAllNodeObjects(nodeList);

        System.out.println(System.currentTimeMillis() + " nodes done" );
        graph.getWayMap().forEach((key, value) -> graph.getEdgesFromWay(value));
        System.out.println(System.currentTimeMillis() + " edges done" );
        graph.fillVertexAndEdgesMap();

        graph.getVertexesFromEdges(root);
        System.out.println(System.currentTimeMillis() + " vertex done" );

        graph.getEdgeWeights();
        System.out.println(System.currentTimeMillis() + " weights done" );

        graph.ConvertEdgeSetIntoHashMap();

        Vertex start = new Vertex( 55.7727061,52.4349619);
        Vertex finish = new Vertex( 55.7500913,52.4219714);

        Set<Long> nearestVertexes = root.getNearestVertexes(start);
        Set<Long> nearestVertexes1 = root.getNearestVertexes(finish);

        if(nearestVertexes.size()==0 || nearestVertexes1.size()==0){
            System.out.println("Точка вне рассматриваемой зоны или произошла ошибка");
        }

        HashMap<Long,Vertex> nearStartvertex = new HashMap<>();
        nearestVertexes.forEach(e->{
            nearStartvertex.put(e,graph.getVertexMap().get(e));
        });
        HashMap<Long,Vertex> nearFinishtvertex = new HashMap<>();
        nearestVertexes1.forEach(e->{
            nearFinishtvertex.put(e,graph.getVertexMap().get(e));
        });
        Vertex startVertex  = graph.getClosestVertex(nearStartvertex,start);
        Vertex finVertex  = graph.getClosestVertex(nearFinishtvertex,finish);

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        dijkstraAlgorithm.setInfDistToVertexes(graph,startVertex);
        dijkstraAlgorithm.CheckVertexes(graph,startVertex,finVertex);

        ArrayList<Vertex> path = (ArrayList<Vertex>) dijkstraAlgorithm.getVertexPath(finVertex);
        dijkstraAlgorithm.printPath(path);

        System.out.println(System.currentTimeMillis());
        System.out.println("Расстояние: " + String.format("%.0f",finVertex.getDistFromStart()) + " м");
        System.out.println("Потраченное время "+  String.format("%.0f",(finVertex.getDistFromStart()/1000)) + " мин если двигаться со скоростью 60 км/ч");
        System.out.println("Madina privet");
    }
}
