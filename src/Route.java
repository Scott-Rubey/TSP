import java.util.*;

public class Route {
    List<Vertex> vertices;
    List<Edge> edges;

    protected Route(){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    protected long calcRouteWeight(){
        long totalWeight = 0;

        for(Edge e:edges)
            totalWeight += e.weight;

        return totalWeight;
    }

    protected void printRoute(){
        Vertex startingVertex = vertices.get(0);
        System.out.print("\n" + startingVertex.name + " --> ");

        for (int i = 1; i < vertices.size(); ++i) {
            Vertex currentVertex = vertices.get(i);
            System.out.print(currentVertex.name);

            //print " --> " if the route has not completed a Hamiltonian Cycle
            if(currentVertex != startingVertex)
                System.out.print(" --> ");
        }
    }
}
