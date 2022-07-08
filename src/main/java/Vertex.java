
public class Vertex implements Comparable<Vertex> {
    private long id;
    private double lat;
    private double lon;
    private Vertex prevVertex;
    private double edgeWeightsFromStart;

    private double timeWeightFromStart;

    private double distWeightFromStart;
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
    public double getEdgeWeightsFromStart() {
        return edgeWeightsFromStart;
    }
    public void setEdgeWeightsFromStart(double edgeWeightsFromStart) {
        this.edgeWeightsFromStart = edgeWeightsFromStart;
    }
    public Vertex getPrevVertex() {
        return prevVertex;
    }
    public void setPrevVertex(Vertex prevVertex) {
        this.prevVertex = prevVertex;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
    // конструктор для вершины на карте
    public Vertex(long id, double lat, double lon) {
        setId(id);
        setLat(lat);
        setLon(lon);
    }
    //конструктор для создания мнимой вершины из заданных пользователем координат
    public Vertex(double lat, double lon){
        setLat(lat);
        setLon(lon);
    }

    @Override
    public int compareTo(Vertex vertex) {
        if(this.getEdgeWeightsFromStart()>vertex.getEdgeWeightsFromStart()){
            return 1;
        } else if(this.getEdgeWeightsFromStart()<vertex.getEdgeWeightsFromStart()){
            return -1;
        } else {
            return 0;
        }
    }

    public String getWKTCoordinates() {
        return lon + " " + lat;
    }

    @Override
    public String toString() {
        return lat + "," + lon;
    }
}
