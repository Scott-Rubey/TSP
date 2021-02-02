import java.util.*;

public class NearNbr {
    //TODO: refactor
    final List<Vertex> vertices;

    protected NearNbr(List<Vertex> vertices){
        this.vertices = vertices;
    }

    //wrapper for nearest neighbor algorithm
    //get lowest route weight by running the algorithm from each vertex
    protected Route nrstNbrDriver(){
        Route bestRoute = null;
        double bestWeight = Double.POSITIVE_INFINITY;

        for(int i = 0; i < vertices.size(); ++i){
            Vertex currentVertex = vertices.get(i);
            Route currentRoute = nrstNbr(currentVertex);
            double currentWeight = currentRoute.calcRouteWeight();
            if(currentWeight < bestWeight){
                bestWeight = currentWeight;
                bestRoute = currentRoute;
            }
        }
        return bestRoute;
    }

    //find a route by greedily searching for edges of lowest weight
    protected Route nrstNbr(Vertex startingVertex){
        Route route = new Route();
        Vertex currentVertex = startingVertex;
        Vertex toVisit;  //destination vertex
        Edge shortestEdge;
        double weight;  //weight to compare
        int count = 1;

        setAllVerticesToUnvisited();
        setCurrentVertexToVisited(currentVertex);
        addVertexToRoute(currentVertex, route);

        while(count < vertices.size()){
            weight = Double.POSITIVE_INFINITY;
            toVisit = null;
            shortestEdge = null;

            for(Edge e:currentVertex.edges) {
                //search unvisited edges for lowest weight
                boolean unvisited = e.destination.visited == false;
                if (unvisited && e.weight < weight) {
                    toVisit = e.destination;
                    weight = e.weight;
                    shortestEdge = e;
                }
            }
            currentVertex = toVisit;
            currentVertex.visited = true;
            addVertexToRoute(currentVertex, route);
            route.edges.add(shortestEdge);

            ++count;
        }

        //return to the start and add the appropriate weight
        addVertexToRoute(startingVertex, route);
        for(Edge e:currentVertex.edges){
            if(e.destination == startingVertex)
                route.edges.add(e);
        }
        return route;
    }

    private void setAllVerticesToUnvisited() {
        for(int i = 0; i < vertices.size(); ++i)
            vertices.get(i).visited = false;
    }

    private void setCurrentVertexToVisited(Vertex currentVertex) {
        currentVertex.visited = true;
    }

    private void addVertexToRoute(Vertex currentVertex, Route route) {
        route.vertices.add(currentVertex);
    }
}