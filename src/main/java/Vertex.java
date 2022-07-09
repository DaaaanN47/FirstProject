
public class Vertex  {
    private long id;
    private double lat;
    private double lon;

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


    public String getWKTCoordinates() {
        return lon + " " + lat;
    }

    @Override
    public String toString() {
        return lat + "," + lon;
    }
}
