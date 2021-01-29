public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();
        NearNbr tsp1 = new NearNbr();
        TwoOptSwap tsp2 = new TwoOptSwap();

        //create graph
        graph.loadGraph();

        //create route using Nearest Neighbor
        Route r1 = tsp1.nrstNbrDriver(graph.getVertices());
        System.out.print("\nRoute using Nearest Neighbor:");
        r1.printRoute();
        System.out.print("\nTotal Weight: " + r1.calcDist() + "\n");

        //optimize using 2-Opt-Swaps
        Route r2 = tsp2.twoOptSwpDriver(r1);
        System.out.print("\nRoute using Two Opt Swap:");
        r2.printRoute();
        System.out.print("\nTotal Weight: " + r2.calcDist() + "\n");

        //Random Swap can be enabled in TwoOptSwap.java
    }
}