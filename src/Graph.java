import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    protected static Scanner fileReader;
    protected List<Vertex> vertices;

    protected Graph(){
        this.vertices = new ArrayList<Vertex>();
    }

    protected List<Vertex> getVertices(){
        return vertices;
    }

    protected void loadGraph(String dataset){
        loadVertices(dataset);
        loadEdges();
    }

    protected void loadVertices(String dataset){
        try
        {
            fileReader = new Scanner(new File("Data/" + dataset));
        }

        catch(FileNotFoundException ex)
        {
            System.out.print("***Could not connect to external data file***\n");
        }

        //load the first vertex and edge
        if(fileReader.hasNext()) {
            Vertex v1 = new Vertex(fileReader.next(), fileReader.nextFloat(), fileReader.nextFloat());
            vertices.add(v1);
            fileReader.nextLine();
        }

        //load subsequent vertices and edges
        while(fileReader.hasNext()) {
            Vertex v1 = new Vertex(fileReader.next(), fileReader.nextFloat(), fileReader.nextFloat());
            vertices.add(v1);
            fileReader.nextLine();
        }

        fileReader.close();
    }

    //create path from each vertex to each other vertex
    //edge weight = distance between Euclidian 2D coordinates
    protected void loadEdges(){
        for(int i = 0; i < vertices.size(); ++i){
            for(int j = 0; j < vertices.size(); ++j){
                Vertex from = vertices.get(i);
                Vertex to = vertices.get(j);
                long weight = calcDist(from.xcoord, from.ycoord, to.xcoord, to.ycoord);

                if(vertices.get(i) != vertices.get(j)){
                    Edge e = new Edge(to, weight);
                    from.edges.add(e);
                }
            }
        }
    }

    //calculate distance between Euclidean 2D coordinates (i.e. edge weight)
    protected long calcDist(float x1, float y1, float x2, float y2){
        double xTotal = Math.pow((x2 - x1), 2);
        double yTotal = Math.pow((y2 - y1), 2);
        double subTotal = xTotal + yTotal;
        double result = Math.sqrt(subTotal);

        return Math.round(result);
    }

    //print the adjacency list
    protected void printAdjList(){
        for(Vertex v:vertices){
            v.print();
        }
    }
}