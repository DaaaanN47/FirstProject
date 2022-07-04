import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgorithm {
    private List<Long> visitedVertexes = new ArrayList<>();
    public void setInfDistToVertexes(Graph graph, Vertex start){
        graph.vertexMap.entrySet().forEach(e->{
            if(e.getValue().equals(start)){
                e.getValue().setDistFromStart(0);
            }else {
                e.getValue().setDistFromStart(Double.MAX_VALUE);
            }
        });
    }
    // потенциально рекурсивный метод который будет принимать в себя граф и одну вершину в самом методе будут перебираться все грани
    //с этой вершиной, на конце грани будут другие точки тоже вызывать этот метод пока точка, в которую придет грань не окажется финишной,
    //  тогда мы спрашиваем(как и для всех граней), ее предыдущее знрачение пути со старта, если новое меньше старого то мы ставим новое значение.
    public void CheckVertexes(Graph graph, Vertex start){
        graph.vertexesAnditsEdges.get(start.getId()).forEach(e->
        {
            //айди второй вершины одной из рассматриваемых граней
            Long id = graph.edgeMap.get(e).getFirstorLastNode(start.getId());
            if(visitedVertexes.contains(id)){
                //проверяем старое расстояние до точки с новым
                if(graph.vertexMap.get(id).getDistFromStart()>start.getDistFromStart() + graph.edgeMap.get(e).getWeight()){
                    //назначаем новый, более короткий путь
                    graph.vertexMap.get(id).setDistFromStart(start.getDistFromStart() + graph.edgeMap.get(e).getWeight());
                    graph.vertexMap.get(id).setPrevVertex(start);
                    CheckVertexes(graph,graph.vertexMap.get(id));
                }
            }
            else{
                //добавляем вершины в список посещенных
                visitedVertexes.add(id);
                graph.vertexMap.get(id).setPrevVertex(start);
                graph.vertexMap.get(id).setDistFromStart(start.getDistFromStart() + graph.edgeMap.get(e).getWeight());
                CheckVertexes(graph,graph.vertexMap.get(id));
            }
        });
    }
    public Vertex FindClosestVertex(Graph graph, double lan, double lon){
        return null;
    }
}
