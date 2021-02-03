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
        System.out.print("\nTotal Weight: " + initialRoute.calcRouteWeight() + "\n");

        //optimize using 2-Opt-Swaps
        Route optimizedRoute = twoOptSwap.runTwoOptSwapAlgthm(initialRoute);
        System.out.print("\nRoute using Two Opt Swap:");
        optimizedRoute.printRoute();
        System.out.print("\nTotal Weight: " + optimizedRoute.calcRouteWeight() + "\n");
    }

    //TODO: add error handling for bad filename
}