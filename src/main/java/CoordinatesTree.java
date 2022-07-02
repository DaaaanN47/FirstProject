import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoordinatesTree {



    int numberOfParents;

    public double getBotLeftLat() {
        return botLeftLat;
    }

    public void setBotLeftLat(double botLeftLat) {
        this.botLeftLat = botLeftLat;
    }

    public double getBotLeftLon() {
        return botLeftLon;
    }

    public void setBotLeftLon(double botLeftLon) {
        this.botLeftLon = botLeftLon;
    }

    public double getTopRightLat() {
        return topRightLat;
    }

    public void setTopRightLat(double topRightLat) {
        this.topRightLat = topRightLat;
    }

    public double getTopRightLon() {
        return topRightLon;
    }

    public void setTopRightLon(double topRightLon) {
        this.topRightLon = topRightLon;
    }

    private double botLeftLat;
    private double botLeftLon;
    private double topRightLat;
    private double topRightLon;
    List<CoordinatesTree> children = new ArrayList<>();
    Set<Long> containedVertexes = new HashSet<>();

    //конструктор для корневого элемента
    public CoordinatesTree(int i){
        this.setRootCoordinates();
        this.getChunks();
    }
    // конструктор для всех детей
    public CoordinatesTree(){

    }

    private void setCoordinates(int quarter, CoordinatesTree parent){
            switch (quarter){
                case 1:
                    this.topRightLat = parent.topRightLat;
                    this.topRightLon = parent.topRightLon;
                    this.botLeftLat = (parent.topRightLat + parent.botLeftLat)/2;
                    this.botLeftLon =(parent.topRightLon+ parent.botLeftLon)/2;
                    break;
                case 2:
                    this.topRightLat = parent.topRightLat;
                    this.topRightLon =(parent.topRightLon+ parent.botLeftLon)/2;
                    this.botLeftLat = (parent.topRightLat+parent.botLeftLat)/2;
                    this.botLeftLon = parent.botLeftLon;
                    break;
                case 3:
                    this.topRightLat = (parent.topRightLat + parent.botLeftLat)/2;
                    this.topRightLon = (parent.topRightLon + parent.botLeftLon)/2;
                    this.botLeftLat = parent.botLeftLat;
                    this.botLeftLon = parent.botLeftLon;
                    break;
                case 4:
                    this.topRightLat = (parent.topRightLat+ parent.botLeftLat)/2;
                    this.topRightLon = parent.topRightLon;
                    this.botLeftLat = parent.botLeftLat;
                    this.botLeftLon = (parent.topRightLon+parent.botLeftLon)/2;
                    break;
            }
    }
    public void setRootCoordinates(){
        this.topRightLat = 90;
        this.topRightLon = 180;
        this.botLeftLat = -90;
        this.botLeftLon = -180;
    }

    public String getChunks(){
        if(this.numberOfParents<12){
            for(int i=0;i<4;i++) {
                CoordinatesTree coordinatesTree = new CoordinatesTree();
                coordinatesTree.numberOfParents=this.numberOfParents+1;
                coordinatesTree.setCoordinates(i+1,this);
                coordinatesTree.getChunks();
                children.add(coordinatesTree);

            }
        }
        return null;
    }
    //метод добавления точки и распределения ее в нужный блок карты
    public String addVertexToChunk(Vertex vertex){
        this.containedVertexes.add(vertex.getId());
        if(!this.children.isEmpty()){
            //проверка на то, в какой части квадарата находится точка верхней или нижней
            if(vertex.getLat()>(this.getTopRightLat()+this.getBotLeftLat())/2){
                if (vertex.getLon()>(this.getTopRightLon()+this.getBotLeftLon())/2){
                    children.get(0).addVertexToChunk(vertex);
                }
                else {
                    children.get(1).addVertexToChunk(vertex);
                }
            }
            else {
                if (vertex.getLon()>(this.getTopRightLon()+this.getBotLeftLon())/2){
                    children.get(3).addVertexToChunk(vertex);
                }
                else{
                    children.get(2).addVertexToChunk(vertex);
                }
            }
        }
        return null;
    }
}
