import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class RandWalk {
    //TODO: refactor
    protected Route randWalkWrapper(List<Vertex> vertices){
        long bestSum = 999999999;
        Route bestRoute = null;

        for(int i = 0; i < 10000; ++i){
            long sum = 0;
            Route r = randomWalk(vertices);
            sum = r.calcRouteWeight();
            if(sum < bestSum) {
                bestSum = sum;
                bestRoute = r;
            }
        }
        return bestRoute;
    }

    protected Route randomWalk(List<Vertex> vertices) {
        Route newRoute = new Route();
        Random random = new Random();
        List<Vertex> copyVerts = new ArrayList<>();

        //copy list of vertices to work with
        for (int i = 0; i < vertices.size(); ++i)
            copyVerts.add(vertices.get(i));

        //create random walk
        while (copyVerts.size() > 0) {
            int rand = random.nextInt(copyVerts.size());
            newRoute.vertices.add(copyVerts.get(rand));
            copyVerts.remove((rand));
        }

        //create cycle
        newRoute.vertices.add(newRoute.vertices.get(0));

        //create new edgelist
        for (int n = 0; n < newRoute.vertices.size(); ++n) {
            for (Edge e : newRoute.vertices.get(n).edges) {
                if (n != newRoute.vertices.size() - 1 && e.to == newRoute.vertices.get(n + 1))
                    newRoute.edges.add(e);
            }
        }
        return newRoute;
    }
}
