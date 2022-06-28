import java.util.*;

public class NodeOSM {
    long id;
    double lat;
    double lon;

    boolean isCrossRoad;

    public boolean isCrossRoad() {
        return isCrossRoad;
    }

    public void setIsCrossRoad(boolean crossRoad) {
        isCrossRoad = crossRoad;
    }

    Set<Long> linkedNodes = new HashSet<>();

     WayOSM way;
     Set<Long> waysHasNode = new HashSet<>();

    public NodeOSM(long id) {
        setId(id);
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
