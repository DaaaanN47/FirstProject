public class Edge {
    long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Vertex getFirstVert() {
        return firstVert;
    }

    public void setFirstVert(Vertex firstVert) {
        this.firstVert = firstVert;
    }

    public Vertex getSecondVert() {
        return secondVert;
    }

    public void setSecondVert(Vertex secondVert) {
        this.secondVert = secondVert;
    }

    public Edge(long id, Vertex firstVert, Vertex secondVert) {
        setId(id);
        setFirstVert(firstVert);
        setSecondVert(secondVert);
    }

    Vertex firstVert;
    Vertex secondVert;
}
