import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        String dataset = args[0];
        Graph graph = new Graph(dataset);
        NearNbr nearNbr = new NearNbr(graph.getVertices());
        TwoOptSwap twoOptSwap = new TwoOptSwap();

        System.out.print("Dataset: " + dataset + "\n");

        //create route using Nearest Neighbor
        Route initialRoute = nearNbr.runNearestNbrAlgthm();
        System.out.print("\nRoute using Nearest Neighbor:");
        initialRoute.printRoute();
        printWeight(initialRoute);

        //optimize using 2-Opt-Swaps
        Route optimizedRoute = twoOptSwap.runTwoOptSwapAlgthm(initialRoute);
        System.out.print("\nRoute using Two Opt Swap:");
        optimizedRoute.printRoute();
        printWeight(optimizedRoute);
    }

    public static void printWeight(Route route){
        System.out.print("\nTotal Weight: " + route.calcRouteWeight() + "\n");
    }

    //TODO: add error handling for bad filename
}