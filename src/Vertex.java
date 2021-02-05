import java.util.*;

public class Vertex {
    protected String name;
    protected Point location;
    protected Boolean visited;
    protected List<Edge> edges;

    protected Vertex(String copyName, Point copyLocation){
        this.name = copyName;
        this.location = copyLocation;
        this.visited = false;
        this.edges = new ArrayList();
    }
}