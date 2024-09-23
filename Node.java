public class Node {

    private String name; // node name
    private double lat; // latitude coordinate
    private double lon; // longitude coordinate

    // Constructors
    public Node() {
        this.name = "";
        this.lat = 0.0;
        this.lon = 0.0;
    }

    public Node(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    // Print node info as a table row
    public void print() {
        System.out.printf("%-15s%-15.2f%-15.2f", this.getName(), this.getLat(), this.getLon());
    }

    // Calculate distance between two nodes using Haversine formula
    public static double distance(Node node1, Node node2) {
        double lat1 = Math.toRadians(node1.getLat());
        double lon1 = Math.toRadians(node1.getLon());
        double lat2 = Math.toRadians(node2.getLat());
        double lon2 = Math.toRadians(node2.getLon());

        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double radius = 6371.0; // Earth's radius in kilometers

        return radius * c;
    }
}

