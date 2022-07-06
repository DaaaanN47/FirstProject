import java.util.*;

public class WayOSM {
    private long id;
    private List<Long> refs = new ArrayList<>();
    private int maxSpeed;
    public int getMaxSpeed() {
        return maxSpeed;
    }
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
    public List<Long> getRefs() {
        return refs;
    }

    public void setRefs(List<Long> refs) {
        this.refs = refs;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WayOSM(long id) {
        this.id = id;
    }


}
