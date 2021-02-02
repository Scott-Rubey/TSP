public class Main {
    public static void main(String[] args) {
        String dataset = args[0];
        Graph graph = new Graph(dataset);
        NearNbr tsp1 = new NearNbr();
        TwoOptSwap tsp2 = new TwoOptSwap();

        System.out.print("Dataset: " + dataset + "\n");

        //create route using Nearest Neighbor
        Route r1 = tsp1.nrstNbrDriver(graph.getVertices());
        System.out.print("\nRoute using Nearest Neighbor:");
        r1.printRoute();
        System.out.print("\nTotal Weight: " + r1.calcRouteWeight() + "\n");

        //optimize using 2-Opt-Swaps
        Route r2 = tsp2.twoOptSwapDriver(r1);
        System.out.print("\nRoute using Two Opt Swap:");
        r2.printRoute();
        System.out.print("\nTotal Weight: " + r2.calcRouteWeight() + "\n");

        //Random Swap can be enabled in TwoOptSwap.java
    }

    //TODO: add error handling for bad filename
}