public class VisitedVertex implements Comparable<VisitedVertex> {
    private long id;
    private long prevVertexId;
    private double edgeWeightsFromStart;
    private double timeWeightFromStart;
    private double distWeightFromStart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPrevVertexId() {
        return prevVertexId;
    }

    public void setPrevVertexId(long prevVertexId) {
        this.prevVertexId = prevVertexId;
    }

    public double getEdgeWeightsFromStart() {
        return edgeWeightsFromStart;
    }

    public void setEdgeWeightsFromStart(double edgeWeightsFromStart) {
        this.edgeWeightsFromStart = edgeWeightsFromStart;
    }

    public double getTimeWeightFromStart() {
        return timeWeightFromStart;
    }

    public void setTimeWeightFromStart(double timeWeightFromStart) {
        this.timeWeightFromStart = timeWeightFromStart;
    }

    public double getDistWeightFromStart() {
        return distWeightFromStart;
    }

    public void setDistWeightFromStart(double distWeightFromStart) {
        this.distWeightFromStart = distWeightFromStart;
    }


    @Override
    public int compareTo(VisitedVertex visitedVertex) {
        if(this.getEdgeWeightsFromStart()> visitedVertex.getEdgeWeightsFromStart()){
            return 1;
        } else if(this.getEdgeWeightsFromStart()< visitedVertex.getEdgeWeightsFromStart()){
            return -1;
        } else {
            return 0;
        }
    }
    public VisitedVertex(long id) {
        this.id = id;
        this.setDistWeightFromStart(Double.MAX_VALUE);
        this.setEdgeWeightsFromStart(Double.MAX_VALUE);
    }
    public VisitedVertex(long id , boolean isStart) {
        this.id = id;
        this.setDistWeightFromStart(0);
        this.setEdgeWeightsFromStart(0);
    }
}
