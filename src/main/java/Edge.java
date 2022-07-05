import java.util.ArrayList;
import java.util.List;

public class Edge {
    private long id;
    private long startVertexId;
    private long finishvertexId;

    private double weight;

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }
    public List<Long> nodesBetweenVertexes = new ArrayList<>();

    public void setId(long id) {
        this.id = id;
    }

    public long getStartVertexId() {
        return startVertexId;
    }

    public void setStartVertId(long firstVert) {
        this.startVertexId = firstVert;
    }

    public long getFinishvertexId() {
        return finishvertexId;
    }
    public void setFinishVertId(long secondVert) {
        this.finishvertexId = secondVert;
    }

    public Edge(long id, long firstVert, long secondVert) {
        setId(id);
        setStartVertId(firstVert);
        setFinishVertId(secondVert);
    }

    public Long getOtherNode(long id){
        if(id==startVertexId){
            return finishvertexId;
        } else {
            return startVertexId;
        }
    }
    public void setNodesInEdge(WayOSM way){
        int startNodeIndex = way.refs.indexOf(startVertexId);
        int finishNodeIndex = way.refs.indexOf(finishvertexId);
        for(int i = startNodeIndex; i<finishNodeIndex+1; i++){
                nodesBetweenVertexes.add(way.refs.get(i));
        }
    }


}
