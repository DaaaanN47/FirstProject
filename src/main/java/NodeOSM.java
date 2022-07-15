import java.util.*;

public class NodeOSM {
    private long id;
    private double lat;
    private double lon;
    private boolean isCrossRoad;
    Set<Long> waysHasNode = new HashSet<>();
    public boolean isCrossRoad() {
        return isCrossRoad;
    }

    public void setIsCrossRoad(){
        isCrossRoad=true;
    }

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
