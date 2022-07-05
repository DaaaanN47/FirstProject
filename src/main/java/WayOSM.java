import java.util.*;

public class WayOSM {
    private long id;
    List<Long> refs = new ArrayList<>();

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
        setId(id);
    }


}
