public class Edge {
    protected Vertex destinationVertex;
    protected long weight;

    protected Edge(Vertex destination, long copyWeight){
        this.destinationVertex = destination;
        this.weight = copyWeight;
    }
}