import java.util.*;

public class WayOSM {
    long id;
    List<Long> refs = new ArrayList<>();
    
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
