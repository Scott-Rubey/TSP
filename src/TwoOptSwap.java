import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TwoOptSwap {
    //TODO: refactor
    protected Route twoOptSwp(Route oldRoute){
        long bestDist = oldRoute.calcRouteWeight();
        long newDist = 0;
        Route newRoute;
        Route curRoute = oldRoute;
        Random random = new Random();
        int initRand = 1;  //set this to 1 in order to run w/ random swap

        for(int i = 1; i < oldRoute.vertices.size()-2; ++i){
            for(int j = i+1; j < oldRoute.vertices.size()-1; ++j){
                //small possibility of a random swap
                int tune = 10;
                int randPos = ThreadLocalRandom.current().nextInt(1,  tune+1);
                if(randPos % tune == 0 && initRand == 1) {
                    int tempi = ThreadLocalRandom.current().nextInt(1, oldRoute.vertices.size()-2);
                    int tempj = ThreadLocalRandom.current().nextInt(i+1, oldRoute.vertices.size()-1);
                    newRoute = twoOptSwpHlp(curRoute, tempi, tempj);
                    newDist = calcTotal(newRoute);
                }

                //otherwise, continue as normal
                else {
                    newRoute = twoOptSwpHlp(curRoute, i, j);
                    newDist = calcTotal(newRoute);
                }

                //if a new best has been found, keep it
                if(newDist < bestDist){
                    bestDist = newDist;
                    curRoute = newRoute;
                }
            }
        }

        return curRoute;
    }

    //performs the actual swap operation
    protected Route twoOptSwpHlp(Route oldRoute, int i, int j){
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

        //for debugging purposes -- time to create edgelist is trivial (< 1 ms in all cases tested)
//        long startTest = System.currentTimeMillis();

        //create new edgelist
        for (int n = 0; n < newRoute.vertices.size(); ++n) {
            for (Edge e : newRoute.vertices.get(n).edges) {
                if (n != newRoute.vertices.size() - 1 && e.to == newRoute.vertices.get(n + 1))
                    newRoute.edges.add(e);
            }
        }

        //for debugging
/*        long endTest = System.currentTimeMillis();
        float edgeTest = (endTest - startTest)/1000f;
        String time = String.format("%.8f", edgeTest);
        System.out.print("\nEdgelist Time: " + time + "\n");  */

        return newRoute;
    }

    //Set 'times' to the number of times you want 2-Opt Swap to run
    //This is beneficial only when Random Swaps are enabled
    protected Route twoOptSwpDriver(Route oldRoute){
        int times = 10;
        Route bestRoute = oldRoute;
        long bestDist = oldRoute.calcRouteWeight();

        for(int i = 0; i < times; ++i){
            Route curRoute = twoOptSwp(bestRoute);
            long curDist = curRoute.calcRouteWeight();
            if(curDist < bestDist){
                bestDist = curDist;
                bestRoute = curRoute;
            }
        }
        return bestRoute;
    }

    protected long calcTotal(Route route){
        return route.calcRouteWeight();
    }
}
