import java.util.*;

public class Vertex {
    protected String name;
    protected float xcoord;
    protected float ycoord;
    protected Boolean visited;
    protected List<Edge> edges;

    protected Vertex(String copyName, float copyX, float copyY){
        this.name = copyName;
        this.xcoord = copyX;
        this.ycoord = copyY;
        this.visited = false;
        this.edges = new ArrayList();
    }
}