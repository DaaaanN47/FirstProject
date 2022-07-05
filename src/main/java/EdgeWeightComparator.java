import java.util.Comparator;

public class EdgeWeightComparator implements Comparator<Vertex> {
    @Override
    public int compare(Vertex vertex, Vertex vertex1) {
        if(vertex.getDistFromStart()>vertex1.getDistFromStart()){
            return 1;
        } else if(vertex.getDistFromStart()<vertex1.getDistFromStart()){
            return -1;
        } else {
            return 0;
        }
    }
}
