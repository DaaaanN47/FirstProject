import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CoordinatesTree {

    List<CoordinatesTree> children = new ArrayList<>();
    Set<Long> containedVertexes = new HashSet<>();

    int numberOfParents;
    private double botLeftLat;
    private double botLeftLon;



    private double topRightLat;
    private double topRightLon;

    public void setCoordinates(int quarter, CoordinatesTree parent){
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
        if(this.numberOfParents<8){
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
    public void addVertexToChunk(Vertex vertex){

    }
}
