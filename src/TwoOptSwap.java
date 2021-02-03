import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TwoOptSwap {
    //TODO: make this a CL option
    boolean randomSwapsOn = true;

    //TODO: refactor
    //Set 'iterations' to the number of times you want 2-Opt Swap to run
    //This is beneficial only when Random Swaps are enabled,
    //as each iteration attempts to find a better route
    protected Route runTwoOptSwapAlgthm(Route nearestNbr){
        int iterations = 10;
        Route bestRoute = nearestNbr;
        long bestWeight = nearestNbr.calcRouteWeight();

        for(int i = 0; i < iterations; ++i){
            Route newRoute = doTwoOptSwaps(bestRoute);
            long newWeight = newRoute.calcRouteWeight();
            if(newWeight < bestWeight){
                bestWeight = newWeight;
                bestRoute = newRoute;
            }
        }
        return bestRoute;
    }

    protected Route doTwoOptSwaps(Route priorBestRoute){
        Route currentRoute = priorBestRoute;
        long priorBestWeight = priorBestRoute.calcRouteWeight();
        Route newRoute;
        long newRouteWeight;

        //perform 2-opt swap heuristic according to en.wikipedia.org/wiki/2-opt (which has been verified for accuracy)
        for(int i = 1; i < priorBestRoute.vertices.size()-2; ++i){
            for(int j = i+1; j < priorBestRoute.vertices.size()-1; ++j){
                //small possibility of triggering a random swap
                boolean randomSwapTriggered = isRandomSwapTriggered();

                if(randomSwapsOn && randomSwapTriggered)
                    newRoute = doRandomSwap(priorBestRoute, currentRoute, i);
                else
                    newRoute = doSwap(currentRoute, i, j);

                newRouteWeight = calcWeight(newRoute);

                //if a new best has been found, keep it
                if(newRouteWeight < priorBestWeight){
                    priorBestWeight = newRouteWeight;
                    currentRoute = newRoute;
                }
            }
        }
        return currentRoute;
    }

    private boolean isRandomSwapTriggered() {
        int tune = 10;  //TODO: make this a CL option
        int possibility = ThreadLocalRandom.current().nextInt(1,  tune+1);

        return possibility % tune == 0;
    }

    private Route doRandomSwap(Route priorBestRoute, Route currentRoute, int i) {
        //perform swap using random values for i and j, rather than their value w/in the for-loops
        int tempi = ThreadLocalRandom.current().nextInt(1, priorBestRoute.vertices.size()-2);
        int tempj = ThreadLocalRandom.current().nextInt(i+1, priorBestRoute.vertices.size()-1);

        Route newRoute = doSwap(currentRoute, tempi, tempj);

        return newRoute;
    }

    //performs the actual swap operation
    protected Route doSwap(Route oldRoute, int i, int j){
        Route newRoute = new Route();
        int temp = j;

        for (int k = 0; k < i; ++k)
            newRoute.vertices.add(oldRoute.vertices.get(k));

        for (int l = i; l <= j; ++l) {
            newRoute.vertices.add(oldRoute.vertices.get(temp));
            --temp;
        }

        for (int m = j + 1; m < oldRoute.vertices.size(); ++m) {
            newRoute.vertices.add(oldRoute.vertices.get(m));
        }

        createNewEdgelist(newRoute);

        return newRoute;
    }

    private void createNewEdgelist(Route newRoute) {
        int numberOfVertices = newRoute.vertices.size();
        for (int i = 0; i < numberOfVertices; ++i) {
            List<Edge> vertexEdges = newRoute.vertices.get(i).edges;
            for (Edge e : vertexEdges) {
                if (i != numberOfVertices - 1 && e.destinationVertex == newRoute.vertices.get(i + 1))
                    newRoute.edges.add(e);
            }
        }
    }

    protected long calcWeight(Route route){
        return route.calcRouteWeight();
    }
}
