import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;

//
public class Main {
    
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        //55.7727061 52.4349619 55.7500913 52.4219714 false
        OsmParser osmParser = new OsmParser();
        Document document = osmParser.getDocument();

        Graph graph = osmParser.graph;
        System.out.println(System.currentTimeMillis());

        Node node = document.getFirstChild();
        NodeList nodeList = node.getChildNodes();

        osmParser.CheckWays(nodeList, args[4]);
        CoordinatesTree root = new CoordinatesTree(nodeList,4);

        System.out.println(System.currentTimeMillis() + " ways done");
        osmParser.getAllNodeObjects(nodeList);

        System.out.println(System.currentTimeMillis() + " nodes done" );
        graph.getWayMap().forEach((key, value) -> graph.getEdgesFromWay(value));
        System.out.println(System.currentTimeMillis() + " edges done" );
        graph.fillVertexAndEdgesMap();

        graph.getVertexesFromEdges(root);
        System.out.println(System.currentTimeMillis() + " vertex done" );

        graph.setEdgeWeights(args[4]);
        System.out.println(System.currentTimeMillis() + " weights done" );

        graph.ConvertEdgeSetIntoHashMap();

        Vertex start = new Vertex( Double.parseDouble(args[0]), Double.parseDouble(args[1]));
        Vertex finish = new Vertex( Double.parseDouble(args[2]), Double.parseDouble(args[3]));

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
        //dijkstraAlgorithm.setInfWeightToVertexes(graph,startVertex, Boolean.valueOf(args[4]));

        PathContainer pathContainer = dijkstraAlgorithm.CheckVertexes(graph,startVertex,finVertex);
        ArrayList<Vertex> path = (ArrayList<Vertex>) pathContainer.getVertexPath(finVertex.getId(), graph);
        pathContainer.printPath(path);

        System.out.println(System.currentTimeMillis());
        System.out.println(pathContainer.visitedpathVertexMap.get(finVertex.getId()).getEdgeWeightsFromStart());
        System.out.println("Madina privet");
    }
}
