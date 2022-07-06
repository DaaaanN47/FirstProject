import java.util.*;

public class DijkstraAlgorithm {

    Queue<Vertex> vertexQueue = new PriorityQueue<>();
    public void setInfDistToVertexes(Graph graph, Vertex start){
        graph.getVertexMap().forEach((key, value) -> {
            if (value.equals(start)) {
                value.setDistFromStart(0);
            } else {
                value.setDistFromStart(Double.MAX_VALUE);
            }
        });
    }
    public void CheckVertexes(Graph graph, Vertex start, Vertex finish){
        vertexQueue.add(start);
        //добавили первую точку в очередь о ваозрастанию,
        //далле пробегаемся по очереди в которой пока один элемент но во время итерации нужно добавить точки которые имеют общее ребро с текущей
        //точка найдена далее она проверяется на то есть ли она в очереди или нет, если ее там нет то проверяется,
        // является ли текущее расстояние от старта меньше чем записанное в вершине, если да то добавляем точку в очередь,
        // если такая точка уже есть в очереди то сравнивается расстояние, записанное в точке в очереди,
        // с расстоянием в рассматриваемой вершине + вес ребра, если оно второе меньше то мы в объекте меняем старые значения на новые.
        //остановкой цикла будет то, что расстояние записанное в рассмотримаемой вершине из очереди будет больше чем расстояние в объекте финиша.
        while(true){
            Vertex vertex = vertexQueue.poll();
            if(vertex.getDistFromStart() > finish.getDistFromStart()){
                break;
            }else {
                graph.getVertexesAndItsEdges().get(vertex.getId()).forEach(edgeId->{
                    Edge edge = graph.edgeMap.get(edgeId);
                    //справшиваю есть ли такая точка в очереди точек
                    Vertex otherEdgeSide = graph.getVertexMap().get(edge.getOtherNode(vertex.getId()));
                    double currentDist = otherEdgeSide.getDistFromStart();
                    double newDist = vertex.getDistFromStart() + edge.getWeight();
                    if(!vertexQueue.contains(otherEdgeSide)){
                        //проверяю является ли расстояние в расмотриваемой точке больше чем в предыдущей точке + вес ребра между ними
                        if(currentDist>newDist){
                            vertexQueue.add(otherEdgeSide);
                            otherEdgeSide.setDistFromStart(newDist);
                            otherEdgeSide.setPrevVertex(vertex);
                        }
                    } else { //если такая точка уже есть в очереди

                        if(currentDist>newDist){
                            otherEdgeSide.setPrevVertex(vertex);
                            otherEdgeSide.setDistFromStart(newDist);
                        }
                    }
                });
            }
        }
    }
    public List<Vertex> getVertexPath(Vertex finish){
        List<Vertex> path = new ArrayList<>();
        getPrevVertexes(finish,path);
        return path;
    }
    private void getPrevVertexes(Vertex vertex, List<Vertex> path) {
        if (vertex.getPrevVertex() != null) {
            path.add(vertex.getPrevVertex());
            getPrevVertexes(vertex.getPrevVertex(), path);
        }
    }
     public void printPath(ArrayList<Vertex> vertices){
        vertices.forEach(e->{
            System.out.println(e.getCoordinatanesStr() + ", " );
        });
     }
}
