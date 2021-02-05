import java.util.ArrayList;

public class Main {
    protected static ArrayList<String> clArgs = new ArrayList();

    public static void main(String[] args) {
        getCommandLineArgs(args);
        boolean errorPresent = searchForCLErrors();
        if(errorPresent)
            alertUserAndExit();

        boolean randomSwapFlag = findFlagIfPresent(clArgs);

        String dataset = args[0];
        Graph graph = new Graph(dataset);
        NearNbr nearNbr = new NearNbr(graph.getVertices());
        TwoOptSwap twoOptSwap = new TwoOptSwap(randomSwapFlag);

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

    private static void getCommandLineArgs(String[] args) {
        for(String arg:args)
            clArgs.add(arg);
    }

    private static boolean searchForCLErrors(){
        boolean errorPresent;

        errorPresent = CLArgsError();
        if(!errorPresent)
            errorPresent = filenameError();
        if(!errorPresent)
            errorPresent = flagError();

        return errorPresent;
    }

    private static boolean CLArgsError(){
        boolean error = false;
        if(clArgs.size() == 0 || clArgs.size() > 2)
            error = true;

        return error;
    }

    private static boolean filenameError() {
        boolean error = false;
        if(clArgs.size() == 1 && !clArgs.get(0).endsWith(".txt"))
            error = true;

        return error;
    }

    private static boolean flagError(){
        boolean error = false;
        if(clArgs.size() == 2){
            if(clArgs.get(1).charAt(0) != '-' || clArgs.get(1).charAt(1) != 'r')
                error = true;
        }
        return error;
    }

    private static void alertUserAndExit(){
        System.out.print("\nErrors present on command line.  Please enter <filename>.txt.  Follow with -r flag to enable random swaps.\n");
        System.exit(1);
    }

    private static boolean findFlagIfPresent(ArrayList clArgs){
        boolean flagPresent = false;

        if(clArgs.contains("-r"))
            flagPresent = true;

        return flagPresent;
    }
}