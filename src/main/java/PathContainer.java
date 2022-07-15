import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathContainer {



    private Map<Long, VisitedVertex> visitedpathVertexMap;
    public Map<Long, VisitedVertex> getVisitedpathVertexMap() {
        return visitedpathVertexMap;
    }
    public PathContainer(){
        visitedpathVertexMap = new HashMap<>();
    }
    public void addOrChangeVertexPath(VisitedVertex visitedVertex){
        if(visitedpathVertexMap.containsKey(visitedVertex.getId())){
            visitedpathVertexMap.get(visitedVertex.getId()).setPrevVertexId(visitedVertex.getPrevVertexId());
            visitedpathVertexMap.get(visitedVertex.getId()).setEdgeWeightsFromStart(visitedVertex.getEdgeWeightsFromStart());
            visitedpathVertexMap.get(visitedVertex.getId()).setDistWeightFromStart(visitedVertex.getDistWeightFromStart());
        }else{
            visitedpathVertexMap.put(visitedVertex.getId(), visitedVertex);
        }
    }
    public List<Vertex> getVertexPath(long finishId, Graph graph){
        List<Vertex> path = new ArrayList<>();
       //path.add(graph.getVertexMap().get(finishId));
        getPrevVertexes(finishId,path, graph);
        return path;
    }
    private void getPrevVertexes(long vertexid, List<Vertex> path, Graph graph) {
        long prevId = visitedpathVertexMap.get(vertexid).getPrevVertexId();
        if ( prevId != 0) {
            List<Vertex> nodeIds = graph.getAllNodesInEdge(vertexid,prevId);
            if(nodeIds.equals(null)){
                System.out.println("vdfvdf");

            }
            path.addAll(nodeIds);
            //path.add(graph.getVertexMap().get(prevId));
            getPrevVertexes(prevId, path , graph);
        }
    }

    public void printPath(ArrayList<Vertex> vertexes){
        vertexes.forEach(e->{
            System.out.println(e.toString() + ", " );
        });
        System.out.println("LINESTRING(" + vertexes.stream().map(Vertex::getWKTCoordinates).collect(Collectors.joining(",")) + ")");
    }
}
