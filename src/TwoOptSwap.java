import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class TwoOptSwap {
    boolean randomSwapFlag;

    protected TwoOptSwap(boolean flag){
        this.randomSwapFlag = flag;
    }

    //Set 'iterations' to the number of times you want 2-Opt Swap to run
    //This is beneficial only when Random Swaps are enabled,
    //as each iteration attempts to find a better route
    protected Route runTwoOptSwapAlgthm(Route nearestNbr){
        int iterations = 10;
        Route bestRoute = nearestNbr;
        long bestWeight = nearestNbr.calcRouteWeight();
        int tune = 0;

        if(randomSwapFlag)
            tune = getTuneValue();

        for(int i = 0; i < iterations; ++i){
            Route newRoute = doTwoOptSwaps(bestRoute, tune);
            long newWeight = newRoute.calcRouteWeight();
            if(newWeight < bestWeight){
                bestWeight = newWeight;
                bestRoute = newRoute;
            }
        }
        return bestRoute;
    }

    protected Route doTwoOptSwaps(Route priorBestRoute, int tune){
        Route currentRoute = priorBestRoute;
        long priorBestWeight = priorBestRoute.calcRouteWeight();
        Route newRoute;
        long newRouteWeight;

        //perform 2-opt swap heuristic according to en.wikipedia.org/wiki/2-opt (which has been verified for accuracy)
        for(int i = 1; i < priorBestRoute.vertices.size()-2; ++i){
            for(int j = i+1; j < priorBestRoute.vertices.size()-1; ++j){
                //small possibility of triggering a random swap
                boolean randomSwapTriggered = isRandomSwapTriggered(tune);

                if(this.randomSwapFlag && randomSwapTriggered)
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

    private int getTuneValue(){
        Scanner input = new Scanner(System.in);
        boolean inputError = true;
        int tune = 0;

        System.out.print("\nEnter the tune value for random swaps (10 is considered roughly optimal): ");

        if(input.hasNextInt()) {
            tune = input.nextInt();
            inputError = checkForInputErrors(tune);
        }

        if(inputError)
            handleInputErrors();

        return tune;
    }

    private void handleInputErrors() {
        printInputError();
        System.exit(1);
    }

    private void printInputError(){
        System.out.print("\nInput error.  Please enter an integer between 1 and 100.\n");
    }

    private boolean isRandomSwapTriggered(int tune) {
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

    protected Route doSwap(Route oldRoute, int i, int j){
        Route newRoute = new Route();
        int temp = j;

        for (int k = 0; k < i; ++k)
            newRoute.vertices.add(oldRoute.vertices.get(k));

        for (int l = i; l <= j; ++l) {
            newRoute.vertices.add(oldRoute.vertices.get(temp));
            --temp;
        }

        for (int m = j + 1; m < oldRoute.vertices.size(); ++m)
            newRoute.vertices.add(oldRoute.vertices.get(m));

        createNewEdgelist(newRoute);

        return newRoute;
    }

    private void createNewEdgelist(Route newRoute) {
        for (int i = 0; i < newRoute.vertices.size(); ++i)
            addCorrectEdge(newRoute, i);
    }

    private void addCorrectEdge(Route newRoute, int i) {
        List<Edge> vertexEdges = newRoute.vertices.get(i).edges;
        boolean nextToLastVertex = i == newRoute.vertices.size() - 1;

        for (Edge edge : vertexEdges) {
            if (!nextToLastVertex && edge.destinationVertex == newRoute.vertices.get(i + 1))
                newRoute.edges.add(edge);
        }
    }

    private boolean checkForInputErrors(int tune){
        boolean error = false;

        if(tune < 1 || tune > 100)
           error = true;

        return error;
    }

    protected long calcWeight(Route route){
        return route.calcRouteWeight();
    }
}
