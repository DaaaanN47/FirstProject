import java.util.*;

public class WayOSM {
    long id;
    Set<Long> refs = new HashSet<Long>();
    Map<Long, NodeOSM> wayNodes = new HashMap<>();
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WayOSM(long id) {
        setId(id);
    }
}
