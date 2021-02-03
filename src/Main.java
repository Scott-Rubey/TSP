public class Main {
    public static void main(String[] args) {
        String dataset = args[0];
        Graph graph = new Graph(dataset);
        NearNbr tsp1 = new NearNbr(graph.getVertices());
        TwoOptSwap tsp2 = new TwoOptSwap();

        System.out.print("Dataset: " + dataset + "\n");

        //create route using Nearest Neighbor
        Route nearestNeighbor = tsp1.runNearestNbrAlgthm();
        System.out.print("\nRoute using Nearest Neighbor:");
        nearestNeighbor.printRoute();
        System.out.print("\nTotal Weight: " + nearestNeighbor.calcRouteWeight() + "\n");

        //optimize using 2-Opt-Swaps
        Route twoOptSwap = tsp2.twoOptSwapDriver(nearestNeighbor);
        System.out.print("\nRoute using Two Opt Swap:");
        twoOptSwap.printRoute();
        System.out.print("\nTotal Weight: " + twoOptSwap.calcRouteWeight() + "\n");
    }

    //TODO: add error handling for bad filename
}