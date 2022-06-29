import java.util.*;

public class Graph {
    //нужны перекрестки и первая и последняя точка вая это точно пересечение. по ним и будет движение
    Map<Long, WayOSM> wayMap = new HashMap<>();
    Map<Long, NodeOSM> nodeMap = new HashMap<>();

    Set<Edge> edges = new HashSet<>();

    //хранит в себе все перектерски(вершины)
    Map<Long, Vertex> vertexMap = new HashMap<>();

    //хранит в себе айдишки вершин и список прилегающих ребер
    Map<Long, ArrayList<Long>> vertexes = new HashMap<>();

    int edgeId=0;

    long nodeId;

    private void ChangeIntermediateNodeId(long id){
        nodeId = id;
    }

    //получаем список всех вершин
    public void getVertexesFromEdges(){
        edges.stream().forEach(e-> {
            Vertex vertex = new Vertex(e.getFirstVertId(),nodeMap.get(e.getFirstVertId()).getLat(),nodeMap.get(e.getFirstVertId()).getLon());
            vertexMap.put(vertex.getId(), vertex);
            Vertex vertex1 = new Vertex(e.getSecondVertId(),nodeMap.get(e.getSecondVertId()).getLat(),nodeMap.get(e.getSecondVertId()).getLon());
            vertexMap.put(vertex1.getId(), vertex1);
        });
    }
    //Получаем мапу <айди вершины, список ребер>, если при пробеге по ребрам мы встречаем точку, которую уже ранее создавали,
    // то просто добавляем к списку ребер текущее и идем дальше
    public void fillVertexMap(){
        edges.stream().forEach(e->
        {
            ArrayList<Long> list = new ArrayList<>();
            list.add(e.getId());
            if(vertexes.containsKey(e.getFirstVertId())){
                vertexes.get(e.getFirstVertId()).add(e.getId());
            }
            else if(vertexes.containsKey(e.getSecondVertId())){
                vertexes.get(e.getSecondVertId()).add(e.getId());
            }else{
                vertexes.put(e.getFirstVertId(),list);
                vertexes.put(e.getSecondVertId(),list);
            }
        });
    }

    public int getCrossRoadRefsCount(WayOSM wayOSM){
        int cRCount=0;
        for(int i = 0; i<wayOSM.refs.size(); i++) {
            if(nodeMap.get(wayOSM.refs.get(i)).isCrossRoad()){
                cRCount++;
            }
        }
        return cRCount;
    }

    public void getEdgesFromWay(WayOSM wayOSM){
        //поле для хранения айдишки точки чтобы знать предыдущую точку конца ребра при находжении следующей вершины.
        ChangeIntermediateNodeId(nodeMap.get(wayOSM.refs.get(0)).getId());
        if(getCrossRoadRefsCount(wayOSM)==2){
            Edge edge = new Edge(edgeId,nodeId,nodeMap.get(wayOSM.refs.get(wayOSM.refs.size()-1)).getId());
            edgeId++;
            edges.add(edge);
        }
        else {
            wayOSM.refs.stream().skip(1).forEach(e->{
                if(nodeMap.get(e).isCrossRoad()){
                    Edge edge = new Edge(edgeId,nodeId,nodeMap.get(e).getId());
                    edgeId++;
                    ChangeIntermediateNodeId(nodeMap.get(e).getId());
                    edges.add(edge);
                }
            });
        }
    }
}
