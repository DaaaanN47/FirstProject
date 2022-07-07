import java.util.*;

public class DijkstraAlgorithm {

    Queue<Vertex> vertexQueue = new PriorityQueue<>();
    //если булевая переменная равна true то вес это расстояние, если false, то время в пути
    public void setInfWeightToVertexes(Graph graph, Vertex start, Boolean weightType){
        if(weightType){
            graph.getVertexMap().forEach((key, value) -> {
                if (value.equals(start)) {
                    value.setDistWeightFromStart(0);
                } else {
                    value.setDistWeightFromStart(Double.MAX_VALUE);
                }
                value.setEdgeWeightsFromStart(value.getDistWeightFromStart());
            });
        }else{
            graph.getVertexMap().forEach((key, value) -> {
                if (value.equals(start)) {
                    value.setTimeWeightFromStart(0);
                } else {
                    value.setTimeWeightFromStart(Double.MAX_VALUE);
                }
                value.setEdgeWeightsFromStart(value.getTimeWeightFromStart());
            });
        }
    }
    // небольшое нововведение в алгоритм тут мы сейчас работает уже не конкретно с лдистанцией ребра а с ее весом,
    // что может быть и дистацией и временем, но чтобы выводить значение пройденного расстояния  в случае если в качестве веса ребра было выбрано время,
    // отдельно ведется подсчет пройденного расстояния в не зависимости было ли выбрано вреям или дистанция в качестве веса ребра
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
            if(vertex.getEdgeWeightsFromStart() > finish.getEdgeWeightsFromStart()){
                break;
            }else {
                graph.getVertexesAndItsEdges().get(vertex.getId()).forEach(edgeId->{
                    Edge edge = graph.edgeMap.get(edgeId);
                    //справшиваю есть ли такая точка в очереди точек
                    Vertex otherEdgeSide = graph.getVertexMap().get(edge.getOtherNode(vertex.getId()));
                    double currentWeight = otherEdgeSide.getEdgeWeightsFromStart();
                    double newWeight = vertex.getEdgeWeightsFromStart() + edge.getWeight();
                    double edgeLength = edge.getLength();
                    if(!vertexQueue.contains(otherEdgeSide)){
                        //проверяю является ли расстояние в расмотриваемой точке больше чем в предыдущей точке + вес ребра между ними
                        if(currentWeight>newWeight){
                            vertexQueue.add(otherEdgeSide);
                            otherEdgeSide.setEdgeWeightsFromStart(newWeight);
                            otherEdgeSide.setPrevVertex(vertex);
                            otherEdgeSide.additionAllPathWeight(vertex.getDistWeightFromStart());
                        }
                    } else { //если такая точка уже есть в очереди
                        if(currentWeight>newWeight){
                            otherEdgeSide.setPrevVertex(vertex);
                            otherEdgeSide.setEdgeWeightsFromStart(newWeight);
                            otherEdgeSide.additionAllPathWeight(vertex.getDistWeightFromStart());
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
     public void printPathDetails(Vertex finVertex, boolean edgeWeightType){
        if(edgeWeightType){
            System.out.println("Расстояние: " + String.format("%.0f",finVertex.getEdgeWeightsFromStart()) + " м");
            System.out.println("Потраченное время "+  String.format("%.0f",(finVertex.getEdgeWeightsFromStart()/1000)/60) + " мин если двигаться со скоростью 60 км/ч");
        } else {
            System.out.println("Расстояние: " + String.format("%.0f",finVertex.getDistWeightFromStart()) + " м");
            System.out.println("Потраченное время "+  String.format("%.0f",(finVertex.getEdgeWeightsFromStart()) + " мин если двигаться со скоростью 60 км/ч"));
        }
     }
}
