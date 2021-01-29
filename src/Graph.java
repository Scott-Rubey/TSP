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

    //edge weight = distance between Euclidian 2D coordinates
    protected void loadEdges(){
        //create path from each vertex...
        for(int i = 0; i < vertices.size(); ++i){
            Vertex vertex1 = vertices.get(i);

            //...to each other vertex
            for(int j = 0; j < vertices.size(); ++j){
                Vertex vertex2 = vertices.get(j);
                long edgeWeight = calcDistance(vertex1.xcoord, vertex1.ycoord, vertex2.xcoord, vertex2.ycoord);

                if(vertices.get(i) != vertices.get(j)){
                    Edge e = new Edge(vertex2, edgeWeight);
                    vertex1.edges.add(e);
                }
            }
        }
    }

    protected void connectToFile() {
        try {
            fileReader = new Scanner(new File("Data/" + dataset));
        }
        catch(FileNotFoundException ex) {
            System.out.print("***Could not connect to external data file***\n");
        }
    }

    //calculate distance between Euclidean 2D coordinates (i.e. edge weight)
    protected long calcDistance(float x1, float y1, float x2, float y2){
        double xTotal = Math.pow((x2 - x1), 2);
        double yTotal = Math.pow((y2 - y1), 2);
        double subTotal = xTotal + yTotal;
        double result = Math.sqrt(subTotal);

        return Math.round(result);
    }

    protected List<Vertex> getVertices(){
        return vertices;
    }

    //for debugging purposes
    protected void printAdjacencyList(){
        for(Vertex v:vertices){
            v.print();
        }
    }
}