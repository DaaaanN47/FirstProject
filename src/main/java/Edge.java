public class Edge {
    private long id;
    private long firstVertId;
    private long secondVertId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFirstVertId() {
        return firstVertId;
    }

    public void setFirstVert(long firstVert) {
        this.firstVertId = firstVert;
    }

    public long getSecondVertId() {
        return secondVertId;
    }

    public void setSecondVert(long secondVert) {
        this.secondVertId = secondVert;
    }

    public Edge(long id, long firstVert, long secondVert) {
        setId(id);
        setFirstVert(firstVert);
        setSecondVert(secondVert);
    }




}
