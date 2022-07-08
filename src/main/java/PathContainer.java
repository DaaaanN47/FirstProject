import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PathContainer {

    Map<Long,PathVertex> visitedpathVertexMap;

    public PathContainer(){
        visitedpathVertexMap = new HashMap<>();
    }
    //метод который принимает
    // 1)id вершины, для которой надо поменять путь,
    // 2)id вершины, которую нужно установить как предыдущую,
    // 3)новый вес ребер до старта
    public void ChangeVertexPath(long vertexToChangeId, long newPrevVertexId, double newEdgeWeightToStart, double newDistance){
        visitedpathVertexMap.get(vertexToChangeId).setPrevVertexId(newPrevVertexId);
        visitedpathVertexMap.get(vertexToChangeId).setEdgeWeightsFromStart(newEdgeWeightToStart);
        visitedpathVertexMap.get(vertexToChangeId).setDistWeightFromStart(newDistance);
    }
    public void addOrChangeVertexPath(PathVertex pathVertex){
        if(visitedpathVertexMap.containsKey(pathVertex.getId())){
            visitedpathVertexMap.get(pathVertex.getId()).setPrevVertexId(pathVertex.getPrevVertexId());
            visitedpathVertexMap.get(pathVertex.getId()).setEdgeWeightsFromStart(pathVertex.getEdgeWeightsFromStart());
            visitedpathVertexMap.get(pathVertex.getId()).setDistWeightFromStart(pathVertex.getDistWeightFromStart());
        }else{
            visitedpathVertexMap.put(pathVertex.getId(),pathVertex);
        }
    }
    public List<Vertex> getVertexPath(long finishId, Graph graph){
        List<Vertex> path = new ArrayList<>();
        getPrevVertexes(finishId,path, graph);
        return path;
    }
    private void getPrevVertexes(long vertexid, List<Vertex> path, Graph graph) {
        long prevId = visitedpathVertexMap.get(vertexid).getPrevVertexId();
        if ( prevId != 0) {
            path.add(graph.getVertexMap().get(prevId));
            getPrevVertexes(prevId, path , graph);
        }
    }

    public void printPath(ArrayList<Vertex> vertexes , Graph graph){
        vertexes.forEach(e->{
            System.out.println(e + ", " );
        });
        System.out.println("LINESTRING(" + vertexes.stream().map(Vertex::getWKTCoordinates).collect(Collectors.joining(",")) + ")");
    }
}
