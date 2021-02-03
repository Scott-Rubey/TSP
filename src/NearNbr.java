import java.util.*;

public class NearNbr {
    final List<Vertex> vertices;

    protected NearNbr(List<Vertex> vertices){
        this.vertices = vertices;
    }

    //wrapper for nearest neighbor algorithm
    //get lowest route weight by running the algorithm from each vertex
    protected Route runNearestNbrAlgthm(){
        Route bestRoute = null;
        double bestWeight = Double.POSITIVE_INFINITY;

        for(int i = 0; i < vertices.size(); ++i){
            Vertex currentVertex = vertices.get(i);
            Route currentRoute = getRoute(currentVertex);
            double currentWeight = currentRoute.calcRouteWeight();

            if(currentWeight < bestWeight){
                bestWeight = currentWeight;
                bestRoute = currentRoute;
            }
        }
        return bestRoute;
    }

    //find a route by greedily searching for edges of lowest weight
    //uses nearest neighbor algorithm
    private Route getRoute(Vertex startingVertex){
        Route route = new Route();
        Vertex currentVertex = startingVertex;
        Vertex nearestNbr;

        //set all vertices to unvisited, set starting vertex to visited and add it to the route
        initializeRoute(route, startingVertex);

        //generate route by iterating through all vertices, each time returning the next vertex on the route
        for(int i = 1; i < vertices.size(); ++i) {
            nearestNbr = getNearestNeighbor(route, currentVertex);
            addNrstNbrToRoute(route, nearestNbr);
            currentVertex = nearestNbr;
        }

        //return to the starting vertex and add the appropriate weight
        completeCycle(startingVertex, route, currentVertex);

        return route;
    }

    private void initializeRoute(Route route, Vertex startVertex) {
        setAllVerticesToUnvisited();
        setVertexToVisited(startVertex);
        addVertexToRoute(startVertex, route);
    }

    private Vertex getNearestNeighbor(Route route, Vertex currentVertex) {
        Vertex nearestNeighbor = null;
        Edge shortestEdge = null;
        double lowestWeight = Double.POSITIVE_INFINITY;

        //search edges for lowest weight
        for(Edge edge:currentVertex.edges) {
            boolean unvisited = !edge.destinationVertex.visited;
            if (unvisited && edge.weight < lowestWeight) {
                nearestNeighbor = edge.destinationVertex;
                lowestWeight = edge.weight;
                shortestEdge = edge;
            }
        }
        route.edges.add(shortestEdge);

        return nearestNeighbor;
    }

    private void addNrstNbrToRoute(Route route, Vertex nearestNeighbor) {
        setVertexToVisited(nearestNeighbor);
        addVertexToRoute(nearestNeighbor, route);
    }

    private void completeCycle(Vertex startingVertex, Route route, Vertex currentVertex) {
        addVertexToRoute(startingVertex, route);
        for(Edge e:currentVertex.edges){
            if(e.destinationVertex == startingVertex)
                route.edges.add(e);
        }
    }

    private void setAllVerticesToUnvisited() {
        for(int i = 0; i < vertices.size(); ++i)
            vertices.get(i).visited = false;
    }

    private void setVertexToVisited(Vertex currentVertex) {
        currentVertex.visited = true;
    }

    private void addVertexToRoute(Vertex currentVertex, Route route) {
        route.vertices.add(currentVertex);
    }
}