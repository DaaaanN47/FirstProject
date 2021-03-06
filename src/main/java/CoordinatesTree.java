import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import java.util.*;

public class CoordinatesTree {

    private final double avgLat;
    private final double avgLon;
    int currentLevel;
    int maxLevel;
    private final double minLat;
    private final double minLon;
    private final double maxLat;
    private final double maxLon;

    private final List<CoordinatesTree> children = new ArrayList<>();
    private final Set<Long> containedVertexes = new HashSet<>();

    //конструктор для корневого элемента
    public CoordinatesTree(NodeList nodeList, int maxLevel){
        this.maxLevel=maxLevel;
        NamedNodeMap attributes = nodeList.item(1).getAttributes();
        this.minLat = Double.parseDouble(attributes.getNamedItem("minlat").getNodeValue());
        this.minLon = Double.parseDouble(attributes.getNamedItem("minlon").getNodeValue());
        this.maxLat = Double.parseDouble(attributes.getNamedItem("maxlat").getNodeValue());
        this.maxLon = Double.parseDouble(attributes.getNamedItem("maxlon").getNodeValue());
        avgLat = (maxLat + minLat) / 2;
        avgLon = (maxLon + minLon) / 2;
        this.createChildren();
    }
    // конструктор для всех детей
    private CoordinatesTree(int maxLevel, int currentLevel, double minLat, double minLon, double maxLat, double maxLon){
        this.maxLevel=maxLevel;
        this.currentLevel= currentLevel;
        this.minLat = minLat;
        this.minLon = minLon;
        this.maxLat = maxLat;
        this.maxLon = maxLon;
        avgLat = (maxLat + minLat) / 2;
        avgLon = (maxLon + minLon) / 2;
        this.createChildren();
    }
    // здесь четверти я нумаровал как в математике
    //метод для назначения координат для верхней правой точки и нижней левой точки
    private void createChildren(){
        if (currentLevel < maxLevel) {
            children.add(new CoordinatesTree(maxLevel,currentLevel + 1, avgLat, minLon, maxLat, avgLon));
            children.add(new CoordinatesTree(maxLevel,currentLevel + 1, avgLat, avgLon, maxLat, maxLon));
            children.add(new CoordinatesTree(maxLevel,currentLevel + 1, minLat, minLon, avgLat, avgLon));
            children.add(new CoordinatesTree(maxLevel,currentLevel + 1, minLat, avgLon, avgLat, maxLon));
        }
    }
    //четверти отмечаются не как в математике первая и вторая четверть меняются местами
    private int getIndex(Vertex vertex){
        int latIndex = vertex.getLat() > avgLat ? 0 : 2;
        int lonIndex = vertex.getLon() > avgLon ? 1 : 0;
        return latIndex + lonIndex;
    }
    //метод добавления точки и распределения ее в нужный блок карты
    public void addVertex(Vertex vertex){
        //this.containedVertexes.add(vertex.getId());
        if(currentLevel<maxLevel){
            children.get(getIndex(vertex)).addVertex(vertex);
        } else {
            containedVertexes.add(vertex.getId());
        }
    }
     public Set<Long> getNearestVertexes(Vertex vertex){
         if(currentLevel<maxLevel){
             // по идее тут нужно сделать проверку на то что лист пустой и закинуть всех потомков из метода выше
             return children.get(getIndex(vertex)).getNearestVertexes(vertex);
         } else {
             return new HashSet<>(containedVertexes);
         }
     }
}
