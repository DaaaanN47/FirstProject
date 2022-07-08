public class PathVertex implements Comparable<PathVertex> {
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
    public int compareTo(PathVertex pathVertex) {
        if(this.getEdgeWeightsFromStart()>pathVertex.getEdgeWeightsFromStart()){
            return 1;
        } else if(this.getEdgeWeightsFromStart()<pathVertex.getEdgeWeightsFromStart()){
            return -1;
        } else {
            return 0;
        }
    }
    public PathVertex(long id) {
        this.id = id;
        this.setDistWeightFromStart(Double.MAX_VALUE);
        this.setEdgeWeightsFromStart(Double.MAX_VALUE);
    }
    public PathVertex(long id , boolean isStart) {
        this.id = id;
        this.setDistWeightFromStart(0);
        this.setEdgeWeightsFromStart(0);
    }

    public PathVertex(long id, long prevVertexId, double edgeWeightsFromStart, double distWeightFromStart) {
        this.id = id;
        this.prevVertexId = prevVertexId;
        this.edgeWeightsFromStart = edgeWeightsFromStart;
        this.distWeightFromStart = distWeightFromStart;
    }
    public String getWKTCoordinates(Graph graph) {
        double lat = graph.getVertexMap().get(getId()).getLat();
        double lon  = graph.getVertexMap().get(getId()).getLon();
        return lon + " " + lat;
    }

}
