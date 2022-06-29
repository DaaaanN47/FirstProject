import java.util.*;

public class NodeOSM {
    private long id;
    private double lat;
    private double lon;

    private boolean isCrossRoad;

    public boolean isCrossRoad() {
        return isCrossRoad;
    }

    public void setIsCrossRoad(boolean crossRoad, long id) {
        isCrossRoad = crossRoad;
        waysHasNode.add(id);
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
