import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Edge {
    private long id;
    private long startVertexId;
    private long finishvertexId;
    private double weight;
    private double length;

    public List<Long> getAllNodesInEdge() {
        return allNodesInEdge;
    }

    private List<Long> allNodesInEdge = new ArrayList<>();
    private int maxSpeed;
    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public long getId() {
        return id;
    }
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
        int startNodeIndex = way.getRefs().indexOf(startVertexId);
        int finishNodeIndex = way.getRefs().indexOf(finishvertexId);
        for(int i = startNodeIndex; i<finishNodeIndex+1; i++){
                allNodesInEdge.add(way.getRefs().get(i));
        }
    }
    public List<Long> getEdgeNodes(long vertexIndex){
        if(allNodesInEdge.indexOf(vertexIndex)==0){
            return allNodesInEdge;
        } else{
            List<Long> nodeIds = new ArrayList<>();
            for(int i = allNodesInEdge.size()-1; i > -1; i--){
                nodeIds.add(allNodesInEdge.get(i));
            }
            return nodeIds;
        }
    }
}
