import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    Map<Long, WayOSM> wayMap = new HashMap<>();
    Map<Long, NodeOSM> nodeMap = new HashMap<>();
    HashMap<Long, List<Long>> linkedNodes;
}
