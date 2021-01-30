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

        return new Vertex(vertexNumber, xCoordinate, ycoordinate);
    }

    protected void addVertexToList(Vertex vertex){
        vertices.add(vertex);
    }

    protected void loadEdges(){
        //create edge from each vertex...
        for(int i = 0; i < vertices.size(); ++i){
            Vertex vertex1 = vertices.get(i);

            //...to each other vertex
            for(int j = 0; j < vertices.size(); ++j){
                Vertex vertex2 = vertices.get(j);
                addEdge(vertex1, vertex2);
            }
        }
    }

    private void addEdge(Vertex vertex1, Vertex vertex2) {
        //only calculate edgeweight and add to vertex1's edgelist if we're dealing with two different vertices
        boolean sameVertex = vertex1 == vertex2;
        if(!sameVertex){
            long edgeWeight = getDistanceBetween(vertex1, vertex2);
            Edge edge = new Edge(vertex2, edgeWeight);
            addEdgeToList(vertex1, edge);  //adding to vertex1's edgelist
        }
    }

    //edge weight = distance between Euclidean 2D coordinates
    protected long getDistanceBetween(Vertex vertex1, Vertex vertex2){
        //TODO: create Point class to encapsulate x/y coordinates
        float x1 = vertex1.xcoord;
        float y1 = vertex1.ycoord;
        float x2 = vertex2.xcoord;
        float y2 = vertex2.ycoord;

        double distance = calcDistanceBetweenPoints(x1, y1, x2, y2);

        return Math.round(distance);
    }

    //calculate distance using pathagorean theorem
    private double calcDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
        double aSquared = Math.pow((x2 - x1), 2);
        double bSquared = Math.pow((y2 - y1), 2);
        double cSquared = aSquared + bSquared;

        return Math.sqrt(cSquared);
    }

    private void addEdgeToList(Vertex vertex1, Edge edge) {
        vertex1.edges.add(edge);
    }

    protected void connectToFile() {
        try {
            fileReader = new Scanner(new File("Data/" + dataset));
        }
        catch(FileNotFoundException ex) {
            System.out.print("***Could not connect to external data file***\n");
        }
    }

    protected List<Vertex> getVertices(){
        return vertices;
    }
}