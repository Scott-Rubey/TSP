import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    protected static Scanner fileReader;
    protected String dataset;
    protected List<Vertex> vertices;

    protected Graph(String dataset){
        this.vertices = new ArrayList<>();
        this.dataset = dataset;
        loadDataset();
    }

    protected void loadDataset(){
        loadVertices();
        loadEdges();
    }

    protected void loadVertices(){
        connectToFile();
        loadFirstVertex();
        loadRemainingVertices();

        fileReader.close();
    }

    protected void connectToFile() {
        try {
            fileReader = new Scanner(new File("Data/" + dataset));
        }
        catch(FileNotFoundException ex) {
            System.out.print("\n***Could not connect to external data file***\n");
            System.exit(1);
        }
    }

    protected void loadFirstVertex() {
        if(fileReader.hasNext()) {
            Vertex vertex = getVertexFromFile();
            addVertexToList(vertex);
            fileReader.nextLine();
        }
    }

    private void loadRemainingVertices() {
        while(fileReader.hasNext()) {
            Vertex vertex = getVertexFromFile();
            addVertexToList(vertex);
            fileReader.nextLine();
        }
    }

    protected Vertex getVertexFromFile() {
        String vertexNumber = fileReader.next();
        float xCoordinate = fileReader.nextFloat();
        float ycoordinate = fileReader.nextFloat();
        Point location = new Point(xCoordinate, ycoordinate);

        return new Vertex(vertexNumber, location);
    }

    protected void addVertexToList(Vertex vertex){
        vertices.add(vertex);
    }

    protected void loadEdges(){
        //create edge from each vertex...
        for(int i = 0; i < vertices.size(); ++i){
            Vertex vertex1 = vertices.get(i);

            //...destinationVertex each other vertex
            for(int j = 0; j < vertices.size(); ++j){
                Vertex vertex2 = vertices.get(j);
                addEdge(vertex1, vertex2);
            }
        }
    }

    private void addEdge(Vertex vertex1, Vertex vertex2) {
        //only calculate edgeweight and add destinationVertex vertex1's edgelist if we're dealing with two different vertices
        boolean sameVertex = vertex1 == vertex2;
        if(!sameVertex){
            long edgeWeight = getDistanceBetween(vertex1, vertex2);
            Edge edge = new Edge(vertex2, edgeWeight);
            addEdgeToList(vertex1, edge);  //adding destinationVertex vertex1's edgelist
        }
    }

    //edge weight = distance between Euclidean 2D coordinates
    protected long getDistanceBetween(Vertex vertex1, Vertex vertex2){
        double distance = calcDistanceBetweenPoints(vertex1, vertex2);
        return Math.round(distance);
    }

    //calculate distance using pathagorean theorem
    private double calcDistanceBetweenPoints(Vertex vertex1, Vertex vertex2) {
        double aSquared = Math.pow((vertex2.location.xCoord - vertex1.location.xCoord), 2);
        double bSquared = Math.pow((vertex2.location.yCoord - vertex1.location.yCoord), 2);
        double cSquared = aSquared + bSquared;
        double distance = Math.sqrt(cSquared);

        return distance;
    }

    private void addEdgeToList(Vertex vertex1, Edge edge) {
        vertex1.edges.add(edge);
    }

    protected List<Vertex> getVertices(){
        return vertices;
    }
}