import java.util.*;

public class NearNbr {
    //TODO: refactor

    //find best possible route by starting from each vertex
    protected Route nrstNbrDriver(List<Vertex> vertices){
        Route bestRoute = null;
        long bestDist = 999999999;

        for(int i = 0; i < vertices.size(); ++i){
            Vertex v = vertices.get(i);
            Route curRoute = nrstNbr(vertices, v);
            long curDist = curRoute.calcRouteWeight();
            if(curDist < bestDist){
                bestDist = curDist;
                bestRoute = curRoute;
            }
        }

        return bestRoute;
    }

    //find a route by greedily searching for edges of lowest weight
    protected Route nrstNbr(List<Vertex> vertices, Vertex v){
        Route route = new Route();
        Vertex start = v;
        Vertex current = start;
        Vertex toVisit;  //vertex to visit
        Edge shortEdge;
        long wt;  //weight to compare
        int count = 1;

        //reset all vertices.visited to false
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
                if (e.to.visited == false && e.weight < wt) {
                    toVisit = e.to;
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

        //return to the start and add the appropriate weight
        route.vertices.add(start);
        for(Edge e:current.edges){
            if(e.to == start)
                route.edges.add(e);
        }

        return route;
    }
}