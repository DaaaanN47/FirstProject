import java.util.*;

public class Graph {
    //нужны перекрестки и первая и последняя точка вая это точно пересечение. по ним и будет движение
    Map<Long, WayOSM> wayMap = new HashMap<>();
    Map<Long, NodeOSM> nodeMap = new HashMap<>();

    Set<Edge> edges = new HashSet<>();

    //хранит в себе все перектерски(вершины)
    Map<Long, Vertex> vertexMap = new HashMap<>();

    //хранит в себе айдишки вершин и список прилегающих ребер
    Map<Long, ArrayList<Long>> vertexesAnditsEdges = new HashMap<>();

    Map<Long,Edge> edgeMap = new HashMap<>();
    int edgeId=0;

    //поле для хранения айдишки точки начала грани.
    long nodeId;


    private void ChangeIntermediateNodeId(long id){
        nodeId = id;
    }

    //получаем список всех вершин, ТУТ НАДО СДЕЛАТЬ ПРОВЕРКУ НА ТО ЕСТЬ ЛИ УЖЕ ТАКАЯ ВЕРШИНА ТАК КАК МЫ МОЖЕМ СОЗДАЬ
    // ОДИНАКОВУЮ ВЕРШИНУ В ВИДЕ КОНЦА ОДНОГО РЕБРА И НАЧАЛА ДРУГОГО
    //НУЖНО БРАТЬ ЭЛЕМЕНТЫ ИЗ КОЛЛЕКЦИИ ВЕРШИН И ЕГО ГРАНЕЙ ЧТОБЫ КОЛИЧЕВО вершин СОВПАДАЛО
    public void getVertexesFromEdges(CoordinatesTree root){
        vertexesAnditsEdges.forEach((key, value) -> {
            Vertex vertex = new Vertex(key, nodeMap.get(key).getLat(), nodeMap.get(key).getLon());
            vertexMap.put(vertex.getId(), vertex);
            root.addVertex(vertex);
        });
    }
    //Получаем мапу <айди вершины, список ребер>, если при пробеге по ребрам мы встречаем точку, которую уже ранее создавали,
    // то просто добавляем к списку ребер текущее и идем дальше
    public void fillVertexAndEdgesMap(){
        edges.forEach(e-> {
            ArrayList<Long> list = new ArrayList<>();
            list.add(e.getId());
            if(vertexesAnditsEdges.containsKey(e.getStartVertexId())){
                vertexesAnditsEdges.get(e.getStartVertexId()).add(e.getId());
            }
            else if(vertexesAnditsEdges.containsKey(e.getFinishvertexId())){
                vertexesAnditsEdges.get(e.getFinishvertexId()).add(e.getId());
            }else{
                vertexesAnditsEdges.put(e.getStartVertexId(),list);
                vertexesAnditsEdges.put(e.getFinishvertexId(),list);
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
            edge.setNodesInEdge(wayOSM);
        }
        else {
            wayOSM.refs.stream().skip(1).forEach(e->{
                if(nodeMap.get(e).isCrossRoad()){
                    Edge edge = new Edge(edgeId,nodeId,nodeMap.get(e).getId());
                    edgeId++;
                    ChangeIntermediateNodeId(nodeMap.get(e).getId());
                    edges.add(edge);
                    edge.setNodesInEdge(wayOSM);
                }
            });
        }
    }
    public void getEdgeWeights(){
        double weight=0;
        edges.forEach(e->{
           calculateWeight(e);
        });
    }
    public void ConvertEdgeSetIntoHashMap(){
        edges.stream().forEach(e->{
            edgeMap.put(e.getId(),e);
        });
    }
    private void calculateWeight(Edge edge) {
    double weight=0;
        NodeOSM node = nodeMap.get(edge.getStartVertexId());
        for(int i=1;i<edge.nodesBetweenVertexes.size();i++) {
            weight= weight + CalculateDistance(node, nodeMap.get(edge.nodesBetweenVertexes.get(i)));
            node = nodeMap.get(edge.nodesBetweenVertexes.get(i));
        }
        edge.setWeight(weight);
    }
    private double CalculateDistance(NodeOSM nodeOSM, NodeOSM nodeOSM1){
        double radius = 6378137;
        double degreeConvert = Math.PI/180;

        double x1  = Math.cos(degreeConvert * nodeOSM.getLat()) * Math.cos(degreeConvert * nodeOSM.getLon());
        double x2  = Math.cos(degreeConvert * nodeOSM1.getLat()) * Math.cos(degreeConvert * nodeOSM1.getLon());

        double y1  = Math.cos(degreeConvert * nodeOSM.getLat()) * Math.sin(degreeConvert * nodeOSM.getLon());
        double y2  = Math.cos(degreeConvert * nodeOSM1.getLat()) * Math.sin(degreeConvert * nodeOSM1.getLon());

        double z1  = Math.sin(degreeConvert * nodeOSM.getLat());
        double z2  = Math.sin(degreeConvert * nodeOSM1.getLat());

        return radius * Math.acos(x1 * x2 + y1 * y2 + z1 * z2);
    }

}
