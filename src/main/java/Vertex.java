public class Vertex {
    private long id;
    private double lat;
    private double lon;

    private Vertex prevVertex;
    private double distFromStart;

    public double getDistFromStart() {
        return distFromStart;
    }
    public void setDistFromStart(double distFromStart) {
        this.distFromStart = distFromStart;
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

    public Vertex(long id, double lat, double lon) {
        setId(id);
        setLat(lat);
        setLon(lon);
    }
}
