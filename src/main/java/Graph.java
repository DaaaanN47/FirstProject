import java.util.*;

public class Graph {
    //нужны перекрестки и первая и последняя точка вая это точно пересечение. по ним и будет движение
    int edgeId=0;

    //поле для хранения айдишки точки начала грани.
    long nodeId;
    double distance;
    Long vertexId;


    public Map<Long, WayOSM> getWayMap() {
        return wayMap;
    }

    public Map<Long, NodeOSM> getNodeMap() {
        return nodeMap;
    }

    private Map<Long, WayOSM> wayMap = new HashMap<>();
    private Map<Long, NodeOSM> nodeMap = new HashMap<>();
    private Set<Edge> edges = new HashSet<>();

    //хранит в себе все перектерски(вершины)
    private Map<Long, Vertex> vertexMap = new HashMap<>();

    public Map<Long, List<Long>> getVertexesAndItsEdges() {
        return vertexesAndItsEdges;
    }

    //хранит в себе айдишки вершин и список прилегающих ребер
    private Map<Long, List<Long>> vertexesAndItsEdges = new HashMap<>();
    Map<Long,Edge> edgeMap = new HashMap<>();
    public Map<Long, Vertex> getVertexMap() {
        return vertexMap;
    }
    public Set<Long> getAllNodesIds() {
        return allNodesIds;
    }
    Set<Long> allNodesIds = new HashSet<>();
    public long getVertexId() {
        return vertexId;
    }

    public void setVertexId(long vertexId) {
        this.vertexId = vertexId;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    private void ChangeIntermediateNodeId(long id){
        nodeId = id;
    }

    //получаем список всех вершин, ТУТ НАДО СДЕЛАТЬ ПРОВЕРКУ НА ТО ЕСТЬ ЛИ УЖЕ ТАКАЯ ВЕРШИНА ТАК КАК МЫ МОЖЕМ СОЗДАЬ
    // ОДИНАКОВУЮ ВЕРШИНУ В ВИДЕ КОНЦА ОДНОГО РЕБРА И НАЧАЛА ДРУГОГО
    //НУЖНО БРАТЬ ЭЛЕМЕНТЫ ИЗ КОЛЛЕКЦИИ ВЕРШИН И ЕГО ГРАНЕЙ ЧТОБЫ КОЛИЧЕВО вершин СОВПАДАЛО
    public void getVertexesFromEdges(CoordinatesTree root){
        vertexesAndItsEdges.forEach((key, value) -> {
            Vertex vertex = new Vertex(key, nodeMap.get(key).getLat(), nodeMap.get(key).getLon());
            vertexMap.put(vertex.getId(), vertex);
            root.addVertex(vertex);
        });
    }

    //Получаем мапу <айди вершины, список ребер>, если при пробеге по ребрам мы встречаем точку, которую уже ранее создавали,
    // то просто добавляем к списку ребер текущее и идем дальше
    public void fillVertexAndEdgesMap(){
        edges.forEach(e-> {
            List<Long> list = vertexesAndItsEdges.getOrDefault(e.getStartVertexId(),new ArrayList<>());
            list.add(e.getId());
            vertexesAndItsEdges.put(e.getStartVertexId(),list);
            list = vertexesAndItsEdges.getOrDefault(e.getFinishvertexId(),new ArrayList<>());
            list.add(e.getId());
            vertexesAndItsEdges.put(e.getFinishvertexId(),list);
        });
    }

    public int getCrossRoadRefsCount(WayOSM wayOSM){
        int cRCount=0;
        for(int i = 0; i<wayOSM.getRefs().size(); i++) {
            if(nodeMap.get(wayOSM.getRefs().get(i)).isCrossRoad()){
                cRCount++;
            }
        }
        return cRCount;
    }

    public void getEdgesFromWay(WayOSM wayOSM){
        //поле для хранения айдишки точки чтобы знать предыдущую точку конца ребра при находжении следующей вершины.
        ChangeIntermediateNodeId(nodeMap.get(wayOSM.getRefs().get(0)).getId());
        if(getCrossRoadRefsCount(wayOSM)==2){
            Edge edge = new Edge(edgeId,nodeId,nodeMap.get(wayOSM.getRefs().get(wayOSM.getRefs().size()-1)).getId());
            edge.setMaxSpeed(wayOSM.getMaxSpeed());
            edgeId++;
            edges.add(edge);
            edge.setNodesInEdge(wayOSM);
        }
        else {
            wayOSM.getRefs().stream().skip(1).forEach(e->{
                if(nodeMap.get(e).isCrossRoad()){
                    Edge edge = new Edge(edgeId,nodeId,nodeMap.get(e).getId());
                    edge.setMaxSpeed(wayOSM.getMaxSpeed());
                    edgeId++;
                    ChangeIntermediateNodeId(nodeMap.get(e).getId());
                    edges.add(edge);
                    edge.setNodesInEdge(wayOSM);
                }
            });
        }
    }
    //если тип веса = true, то вес ребра будет равнятся его длине, если false то вес ребра будет равен времени в пути(в минутах) с учетом типа дороги и разреешенной максимальной скорости
    public void setEdgeWeights(String weightType){
        if(weightType.equals("Расстояние")){
            edges.forEach(e->{
                calculateEdgeLength(e);
                e.setWeight(e.getLength());
            });
        } else {
            edges.forEach(e->{
                calculateEdgeLength(e);
                e.setWeight((e.getLength()/1000)/e.getMaxSpeed());
            });
        }
    }

    public void ConvertEdgeSetIntoHashMap(){
        edges.forEach(e->{
            edgeMap.put(e.getId(),e);
        });
    }

    private void calculateEdgeLength(Edge edge) {
    double length=0;
        NodeOSM node = nodeMap.get(edge.getStartVertexId());
        for(int i=1;i<edge.nodesBetweenVertexes.size();i++) {
            length= length + CalculateDistance(node.getLat(), node.getLon(), nodeMap.get(edge.nodesBetweenVertexes.get(i)).getLat(),nodeMap.get(edge.nodesBetweenVertexes.get(i)).getLon());
            node = nodeMap.get(edge.nodesBetweenVertexes.get(i));
        }
        edge.setLength(length);
    }

    private double CalculateDistance(double strLat, double strLon, double finLat, double finLon){
        double radius = 6378137;
        double degreeConvert = Math.PI/180;

        double x1  = Math.cos(degreeConvert * strLat) * Math.cos(degreeConvert * strLon);
        double x2  = Math.cos(degreeConvert * finLat) * Math.cos(degreeConvert * finLon);

        double y1  = Math.cos(degreeConvert * strLat) * Math.sin(degreeConvert * strLon);
        double y2  = Math.cos(degreeConvert * finLat) * Math.sin(degreeConvert * finLon);

        double z1  = Math.sin(degreeConvert * strLat);
        double z2  = Math.sin(degreeConvert * finLat);

        return radius * Math.acos(x1 * x2 + y1 * y2 + z1 * z2);
    }

    public Vertex getClosestVertex(HashMap<Long,Vertex> vertexMap, Vertex vertex){
        setDistance(Double.MAX_VALUE);
        vertexMap.forEach((key, value) -> {
            double newDist = CalculateDistance(vertex.getLat(), vertex.getLon(), value.getLat(), value.getLon());
            if (getDistance() > newDist ) {
                setDistance(newDist);
                setVertexId(value.getId());
            }
        });
        return vertexMap.get(getVertexId());
    }
}
