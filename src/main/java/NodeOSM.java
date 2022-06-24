public class NodeOSM {
    long id;
    double lat;
    double lon;

    public NodeOSM(long id, double lat, double lon) {
        setId(id);
        setLat(lat);
        setLon(lon);
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
}
