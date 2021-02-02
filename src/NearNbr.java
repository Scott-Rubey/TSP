import java.util.*;

public class NearNbr {
    //TODO: refactor
    final List<Vertex> vertices;

    protected NearNbr(List<Vertex> vertices){
        this.vertices = vertices;
    }

    //wrapper for nearest neighbor algorithm
    //get lowest route weight by running the algorithm from each vertex
    protected Route runNearestNeighbor(){
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
    protected Route getRoute(Vertex startingVertex){
        Route route = new Route();
        Vertex currentVertex = startingVertex;

        //set all vertices to unvisited, set starting vertex to visited and add it to the route
        initializeRoute(route, startingVertex);

        //generate route by iterating through all vertices, each time returning the next vertex on the route
        for(int i = 1; i < vertices.size(); ++i)
            currentVertex = getNextVertex(route, currentVertex);

        //return to the start and add the appropriate weight
        completeCycle(startingVertex, route, currentVertex);

        return route;
    }

    private Vertex getNextVertex(Route route, Vertex currentVertex) {
        double lowestWeight;
        Vertex candidateVertex;  //destination vertex being considered
        Edge shortestEdge;
        lowestWeight = Double.POSITIVE_INFINITY;
        candidateVertex = null;
        shortestEdge = null;

        //search edges for lowest weight
        for(Edge edge:currentVertex.edges) {
            boolean unvisited = !edge.destinationVertex.visited;
            if (unvisited && edge.weight < lowestWeight) {
                candidateVertex = edge.destinationVertex;
                lowestWeight = edge.weight;
                shortestEdge = edge;
            }
        }
        currentVertex = candidateVertex;
        setVertexToVisited(currentVertex);
        addVertexToRoute(currentVertex, route);
        route.edges.add(shortestEdge);

        return currentVertex;
    }

    private void initializeRoute(Route route, Vertex startVertex) {
        setAllVerticesToUnvisited();
        setVertexToVisited(startVertex);
        addVertexToRoute(startVertex, route);
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

    private void completeCycle(Vertex startingVertex, Route route, Vertex currentVertex) {
        addVertexToRoute(startingVertex, route);
        for(Edge e:currentVertex.edges){
            if(e.destinationVertex == startingVertex)
                route.edges.add(e);
        }
    }
}