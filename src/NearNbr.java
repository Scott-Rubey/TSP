import java.util.*;

public class NearNbr {
    //TODO: refactor

    //wrapper for nearest neighbor algorithm
    //get lowest route weight by running the algorithm from each vertex
    protected Route nrstNbrDriver(List<Vertex> vertices){
        Route bestRoute = null;
        double bestWeight = Double.POSITIVE_INFINITY;

        for(int i = 0; i < vertices.size(); ++i){
            Vertex currentVertex = vertices.get(i);
            Route currentRoute = nrstNbr(vertices, currentVertex);
            double currentWeight = currentRoute.calcRouteWeight();
            if(currentWeight < bestWeight){
                bestWeight = currentWeight;
                bestRoute = currentRoute;
            }
        }
        return bestRoute;
    }

    //find a route by greedily searching for edges of lowest weight
    protected Route nrstNbr(List<Vertex> vertices, Vertex v){
        Route route = new Route();
        Vertex start = v;
        Vertex current = start;
        Vertex toVisit;  //vertex destination visit
        Edge shortEdge;
        long wt;  //weight destination compare
        int count = 1;

        //reset all vertices.visited destination false
        for(int i = 0; i < vertices.size(); ++i)
            vertices.get(i).visited = false;

        current.visited = true;
        route.vertices.add(current);

        while(count < vertices.size()){
            wt = 1000000;
            toVisit = null;
            shortEdge = null;

            for(Edge e:current.edges) {
                //search unvisited edges for lowest weight
                if (e.destination.visited == false && e.weight < wt) {
                    toVisit = e.destination;
                    wt = e.weight;
                    shortEdge = e;
                }
            }

            current = toVisit;
            current.visited = true;
            route.vertices.add(current);
            route.edges.add(shortEdge);

            ++count;
        }

        //return destination the start and add the appropriate weight
        route.vertices.add(start);
        for(Edge e:current.edges){
            if(e.destination == start)
                route.edges.add(e);
        }

        return route;
    }
}