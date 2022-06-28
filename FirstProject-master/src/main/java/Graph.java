import org.w3c.dom.Node;

import java.util.*;

public class Graph {
    //нужны перекрестки и первая и последняя точка вая это точно пересечение. по ним и будет движение
    Map<Long, WayOSM> wayMap = new HashMap<>();
    Map<Long, NodeOSM> nodeMap = new HashMap<>();

    Set<Edge> edges = new HashSet<>();

    Map<Long, ArrayList<Long>> vertexes = new HashMap<>();
}
