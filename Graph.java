import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph {
    private int n; // number of nodes
    private int m; // number of arcs
    private ArrayList<Node> nodes; // ArrayList of nodes
    private boolean[][] A; // adjacency matrix
    private double[][] C; // cost matrix

    public Graph() {
        this.n = 0;
        this.m = 0;
        this.nodes = new ArrayList<>();
    }

    public Graph(int n) {
        init(n);
        this.nodes = new ArrayList<>();
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setM(int m) {
        this.m = m;
    }

    public void setArc(int i, int j, boolean b) {
        A[i][j] = b;
        A[j][i] = b;
    }

    public void setCost(int i, int j, double c) {
        C[i][j] = c;
        C[j][i] = c;
    }

    public int getNumGraphs() {
        return nodes.size();
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public boolean getArc(int i, int j) {
        return this.A[i][j];
    }

    public double getCost(int i, int j) {
        return C[i][j];
    }

    public Node getNode(int i) {
        if (i >= 1 && i <= n) {
            return nodes.get(i - 1);
        }
        return null;
    }

    // Check if the graph is valid
    public boolean isValidGraph() {
        return !hasDuplicateNodes() && !hasDuplicateCoordinates() && isValidCoordinates();
    }

    // Check for duplicate nodes (names)
    private boolean hasDuplicateNodes() {
        Set<String> nodeNames = new HashSet<>();
        for (Node node : nodes) {
            if (!nodeNames.add(node.getName())) {
                System.out.println("\nERROR: Graph contains nodes with duplicate names!");
                return true;
            }
        }
        return false;
    }

    // Check for duplicate coordinates
    private boolean hasDuplicateCoordinates() {
        Set<String> coordinatesSet = new HashSet<>();
        for (Node node : nodes) {
            String coordinates = node.getLat() + "," + node.getLon();
            if (!coordinatesSet.add(coordinates)) {
                System.out.println("\nERROR: Graph contains nodes with duplicate coordinates!");
                return true;
            }
        }
        return false;
    }

    // Helper method to check for invalid coordinates
    private boolean isValidCoordinates() {
        for (Node node : nodes) {
            double lat = node.getLat();
            double lon = node.getLon();

            // Check for valid latitude and longitude ranges
            if (!(lat >= -90.0 && lat <= 90.0) || !(lon >= -180.0 && lon <= 180.0)) {
                System.out.println("\nERROR: Invalid coordinates for node " + node.getName() + "!");
                return false;
            }
        }
        return true;
    }

    // initializing
    public void init(int n) {
        this.n = n;
        this.m = 0;
        this.nodes = new ArrayList<>(n);
        this.A = new boolean[n + 1][n + 1];
        this.C = new double[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                A[i][j] = false;
                A[j][i] = false;
                C[i][j] = 0.0;
                C[j][i] = 0.0;
            }
        }
    }

    // resetting current graph so the user can input new nodes and arcs
    public void reset() {
        if (getN() == 0) {
            System.out.println("\nERROR: No graph has been loaded!\n");
            return;
        }
        nodes.clear();
        setN(0);
        setM(0);
        this.A = new boolean[this.n + 1][this.n + 1];
        this.C = new double[this.n + 1][this.n + 1];
        System.out.print("\n");
    }

    // method to check if an arc exists
    public boolean existsArc(int i, int j) {
        // Adjust indices to account for 1-based node numbering
        i--;
        j--;

        // Ensure that adjusted indices are within the array bounds
        if (i >= 0 && i < n && j >= 0 && j < n) {
            return this.A[i][j];
        } else {
            // Handle the case where indices are out of bounds
            return false;
        }
    }

    // method to check if a node exists
    public boolean existsNode(Node t) {
        if (nodes.size() == 0)
            return false;
        for (int i = 1; i <= nodes.size(); i++) {
            String curr_name = nodes.get(i - 1).getName();
            double curr_lat = nodes.get(i - 1).getLat();
            double curr_lon = nodes.get(i - 1).getLon();

            if (curr_name.equals(t.getName())) {
                return true;
            }

            if (((t.getLat() == curr_lat) && (t.getLon() == curr_lon))) {
                return true;
            }
        }
        return false;
    }

    // method to add arcs to the existing graph
    public boolean addArc(int i, int j) {
        if (i != j && !A[i][j]) {
            A[i][j] = true;
            A[j][i] = true;
            m++;
            return true;
        }
        return false;
    }

    // method to remove an arc from the existing graph
    public void removeArc(int k) {
        int counter = 0;
        for (int r = 0; r <= n; r++) {
            for (int c = r + 1; c <= n; c++) {
                if (A[r][c]) {
                    counter++;
                    if (counter == k) {
                        A[r][c] = false;
                        A[c][r] = false;
                        C[r][c] = 0.0;
                        C[c][r] = 0.0;
                        m--;
                        System.out.print("\nArc " + k + " removed!\n\n");
                        printArcs();
                        System.out.print("\n");
                        // return true;
                    }
                }
            }
        }
    }

    // method to add a node
    public boolean addNode(Node t) {
        nodes.add(t);
        return true;
    }

    // method to print the graph in the display function (in the main class)
    public void print() {
        int numOfCities = this.getN();
        int numOfArcs = this.getM();
        System.out.printf("Number of nodes: %d%n", numOfCities); // display the number of nodes
        System.out.printf("Number of arcs: %d%n", numOfArcs); // display the number of arcs

        // print nodes and arcs
        this.printNodes();
        this.printArcs();
        System.out.print("\n");
    }

    // method to print nodes into a table
    public void printNodes() {
        System.out.print("\nNODE LIST (fix this)");
        System.out.println("\nNo.               Name        Coordinates");
        System.out.println("-----------------------------------------");

        for (int i = 1; i <= n; i++) {
            Node city = getNode(i);
            System.out.printf("%3d", i);
            city.print();
            System.out.print("\n");
        }
    }

    // method to print arcs in a table
    public void printArcs() {
        System.out.print("\nARC LIST");
        System.out.println("\nNo.    Cities       Distance");
        System.out.println("----------------------------");

        int arcNumber = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (A[i][j]) {
                    Node city1 = getNode(i);
                    Node city2 = getNode(j);
                    double distance = Node.distance(city1, city2);
                    String format_dist = String.format("%.2f", distance);
                    System.out.printf("%3s%10s%15s", arcNumber, i + "-" + j, format_dist);
                    arcNumber++;
                    System.out.print("\n");
                }
            }
        }
    }

    // method to check the path of cities inputted and make sure it is a possible path
    public boolean checkPath(int[] P) {

        boolean works = true;

        if (P[0] != P[P.length - 1]) {
            System.out.println("\nERROR: Start and end cities must be the same!\n");
            works = false;
            return works;
        }

        for (int i = 0; i < P.length - 2; i++) {
            for (int j = i + 1; j < P.length - 1; j++) {
                if (P[i] == P[j]) {
                    System.out.println("\nERROR: Cities cannot be visited more than once!");
                    System.out.print("ERROR: Not all cities are visited!\n");
                    System.out.print("\n");
                    works = false;
                    return works;
                }
            }
        }

        for (int i = 0; i <= n - 1; i++) {
            int city1 = P[i];
            int city2 = P[i + 1];

            if (!existsArc(city1, city2)) {
                System.out.printf("\nERROR: Arc %d-%d does not exist!\n", city1, city2);
                System.out.print("\n");
                works = false;
                return works;
            }
        }
        return works;
    }

    public double arcCost(int i, int j) {
        return C[i][j];
    }

    // method to return the cost of the path
    public double pathCost(int[] P) {
        double cost = 0.0;

        for (int i = 0; i < P.length - 1; i++) {
            int from = P[i];
            int to = P[i + 1];

            // Check if indices are within bounds
            if (from >= 0 && from <= n && to >= 0 && to <= n) {
                cost += C[from][to];
            } else {
                System.out.printf("ERROR: Invalid indices in the path. (from=%d, to=%d, n=%d)%n", from, to, n);
                return -1.0; // or another appropriate value to indicate an error
            }
        }

        return cost;
    }
}

