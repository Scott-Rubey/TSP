public class Edge {
    protected Vertex destination;
    protected long weight;

    protected Edge(Vertex copyTo, long copyWeight){
        this.destination = copyTo;
        this.weight = copyWeight;
    }
}