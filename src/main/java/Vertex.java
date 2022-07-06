
public class Vertex implements Comparable<Vertex> {
    private long id;
    private double lat;
    private double lon;
    private Vertex prevVertex;
    private double distFromStart;
    private int maxSpeed;
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    private String coordinatanesStr;
    public String getCoordinatanesStr() {
        return coordinatanesStr;
    }
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
    // конструктор для вершины на карте
    public Vertex(long id, double lat, double lon) {
        setId(id);
        setLat(lat);
        setLon(lon);
        coordinatanesStr= lat + "," + lon;
    }
    //конструктор для создания мнимой вершины из заданных пользователем координат
    public Vertex(double lat, double lon){
        setLat(lat);
        setLon(lon);
    }

    @Override
    public int compareTo(Vertex vertex) {
        if(this.getDistFromStart()>vertex.getDistFromStart()){
            return 1;
        } else if(this.getDistFromStart()<vertex.getDistFromStart()){
            return -1;
        } else {
            return 0;
        }
    }
}
