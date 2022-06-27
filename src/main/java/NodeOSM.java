import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeOSM {
    long id;
    double lat;
    double lon;

    Map<Long, NodeOSM> linkedNodes = new HashMap<>();

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
