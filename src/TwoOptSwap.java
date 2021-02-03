import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TwoOptSwap {
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
        long priorBestWeight = priorBestRoute.calcRouteWeight();
        long newRouteWeight;
        Route newRoute;
        Route currentRoute = priorBestRoute;
        boolean doRandomSwap = true;  //set this to true in order to run w/ random swap
        //TODO: make this a CL option

        //perform 2-opt swap heuristic according to en.wikipedia.org/wiki/2-opt (which has been verified for accuracy)
        for(int i = 1; i < priorBestRoute.vertices.size()-2; ++i){
            for(int j = i+1; j < priorBestRoute.vertices.size()-1; ++j){
                //small possibility of triggering a random swap
                int tune = 10;  //TODO: make this a CL option
                int possibility = ThreadLocalRandom.current().nextInt(1,  tune+1);

                boolean swapTriggered = possibility % tune == 0;
                if(doRandomSwap && swapTriggered) {
                    //perform swap using random values for i and j, rather than their value w/in the for-loops
                    int tempi = ThreadLocalRandom.current().nextInt(1, priorBestRoute.vertices.size()-2);
                    int tempj = ThreadLocalRandom.current().nextInt(i+1, priorBestRoute.vertices.size()-1);
                    newRoute = doSwap(currentRoute, tempi, tempj);
                    newRouteWeight = calcWeight(newRoute);
                }

                //otherwise, continue as normal
                else {
                    newRoute = doSwap(currentRoute, i, j);
                    newRouteWeight = calcWeight(newRoute);
                }

                //if a new best has been found, keep it
                if(newRouteWeight < priorBestWeight){
                    priorBestWeight = newRouteWeight;
                    currentRoute = newRoute;
                }
            }
        }

        return currentRoute;
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
