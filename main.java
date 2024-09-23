import java.io.*;

public class main {
	public static BufferedReader reader = new BufferedReader (new InputStreamReader(System.in));
	
	public static void main(String[] args) {
		Graph graph = new Graph();
		
		try {
			while (true) {
				displayMenu();	
				//cases for each option of menu
				String choice = reader.readLine();
				 switch (choice) {
                 case "G":
                 case "g":
                     createGraph(graph);
                     break;
                 case "C":
                 case "c":
                     editCities(graph);
                     break;
                 case "A":
                 case "a":
                     editArcs(graph);
                     break;
                 case "D":
                 case "d":
                     displayGraph(graph);
                     break;
                 case "R":
                 case"r":
                     graph.reset();
                     break;
                 case "P":
                 case "p":
                     tryPath(graph);
                     break;
                 case "Q":
                 case "q":
                	 System.out.print("\n");
                	 System.out.print("Ciao!");
                	 reader.close();
                	 System.exit(0);
                default:
                	System.out.println("\nERROR: Invalid menu choice!\n");
				}
			}
			
		}catch (NumberFormatException e) {
			System.out.printf("\nERROR: Invalid menu choice!\n");
		}catch (IOException e) {
			System.out.printf("\nERROR: Invalid menu choice!\n");
		}
	}

	//create menu for user to choose from
public static void displayMenu() {
	System.out.println("   JAVA TRAVELING SALESMAN PROBLEM V1");
    System.out.println("G - Create graph");
    System.out.println("C - Edit cities");
    System.out.println("A - Edit arcs");
    System.out.println("D - Display graph info");
    System.out.println("R - Reset graph");
    System.out.println("P - Enter salesman's path");
    System.out.println("Q - Quit");
    
    System.out.print("\nEnter choice: ");
    
}

public static void createGraph(Graph G) throws IOException {
    try {
    int numCities;
	while(true) { 
		//ask user for number of cities
        numCities = getInteger("\nEnter number of cities (0 to quit): ", 0, Integer.MAX_VALUE); // read number of cities
        if (numCities == 0) {
        	System.out.print("\n");
        	return;
        }
        break;
	}
       
        G.init(numCities);

        for (int i = 1; i <= numCities; i++) {
            while (true) {
	        	Node city = new Node();
	        	
	            System.out.print("\nCity " + i + ":");
	        	//name of city

	            System.out.print("\n   Name: ");
	            city.setName(reader.readLine());
	            
	            //latitude of city 
	            city.setLat(getDouble("   latitude: ", -90.00, 90.00));
	            
	            //longitude of city 
	            city.setLon(getDouble("   longitude: ", -180.00, 180.00));
	
	            
	            if(G.existsNode(city)) {
	        		System.out.print("ERROR: City name and/or coordinates already exist!\n");
	        		
	            }
	            else {
	            	G.addNode(city);
	            	break;
	            	
	     	    }
	            
	           }
        }
            
        // Ask the user if they want to add arcs
        System.out.print("\nNow add arcs:");
        System.out.print("\n");
        G.printNodes();
        System.out.print("\n");
        boolean flag = true;
        while (flag) {
            int firstCityIndex = getInteger("Enter first city index (0 to quit): ", 0, numCities);

            if (firstCityIndex == 0) {
            	flag=false;
            	System.out.print("\n");
                break; // Quit adding arcs
            }
            
            

            int secondCityIndex = getInteger("Enter second city index (0 to quit): ", 0, numCities);

            if (secondCityIndex == 0) {
            	flag=false;
            	System.out.print("\n");
                break; // Quit adding arcs

            }
            
            //error when user enters in same number for first and second city 
            if (firstCityIndex == secondCityIndex) {
                System.out.println("\nERROR: A city cannot be linked to itself!\n");
            } else {
                boolean added = G.addArc(firstCityIndex, secondCityIndex);
                if (added) {
                    System.out.println("\nArc " + firstCityIndex + "-" + secondCityIndex + " added!\n");
                } else {
                    System.out.println("\nERROR: Arc already exists!\n");
                }
            }
        }
        
    
    } catch (NumberFormatException e) {
    	System.out.printf("\nERROR: Invalid menu choice!");
    }
    }

public static void editCities(Graph G) {
    if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
    }
    
    G.printNodes();
    System.out.print("\n");
        
    	//user enters node of city they want to edit 
        int nodeNumber = getInteger("Enter city to edit (0 to quit): ", 0, G.getN());
        if (nodeNumber == 0) {
        displayMenu();
        }
    	
        Node city = G.getNode(nodeNumber);
        if (city == null) {
            System.out.println("\nERROR: Node does not exist!");
            return;
            
     }
        //city.userEdit();

} 

// when the user wants to change one of the arcs 
public static void editArcs(Graph G) {
    if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
    }
    
    
        
    while (true) {
    	G.printArcs();
    	// menu for edit arcs
        

        try {
        	System.out.println("\nEDIT ARCS");
            System.out.println("A - Add arc");
            System.out.println("R - Remove arc");
            System.out.println("Q - Quit");
            System.out.print("\nEnter choice: ");
            String choice = reader.readLine();
            
            // cases for different choices for edit arc menu
            switch (choice) {
                case "A":
                case "a":
                    addArcs(G);
                    break;
                case "R":
                case "r":
                    removeArc(G);
                    break;
                case "Q":
                case "q":
                	System.out.print("\n");
                    return;
                default:
                    System.out.print("\n");
                	System.out.println("ERROR: Invalid menu choice!");
                    break;
            }
        } catch (IOException e) { 
        	System.out.println("\nError: Invalid menu choice!");
        } catch (NumberFormatException e) {
        	System.out.printf("\nERROR: Invalid menu choice!");
        }
        
    }
}

//when user wants to add arcs to existing graph 
public static void addArcs(Graph G) throws IOException {
    if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
    }
    G.printNodes();
    System.out.print("\n");

    while (true) {
    	//user enters first and second node for arc they want to add
    	int startNode = getInteger("Enter first city index (0 to quit): ", 0, G.getN());
	    if (startNode == 0) {
	    	return;
	    }
	    int endNode = getInteger("Enter second city index (0 to quit): ", 0, G.getN());
	    if (endNode == 0) {
	    	return;
	    }
	    

	    
	    for (int i = 0; i < startNode;) {
	    	//error when first and second inputed number are the same 
	        if (startNode == endNode) {
	            System.out.println("\nERROR: A city cannot be linked to itself!\n");
	        } else {
	            boolean added = G.addArc(startNode, endNode);
	            if (added) {
	                System.out.print("\nArc " + startNode + "-" + endNode + " added!\n");
	                System.out.print("\n");
	            } else {
	                System.out.println("\nERROR: Arc already exists!\n");
	                break;	               
	            }
	        }
	        break;
	    }
	    
	}
}

//when user wants to remove arc from current graph
public static void removeArc(Graph G) {
    if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
    }
    G.printArcs();
    System.out.print("\n");
    

    
    while (true) {

        int arc_removed = getInteger("Enter arc to remove (0 to quit): ", 0, G.getM());
        if (arc_removed == 0) {
            break;
        }
        if (arc_removed > 0) {
        	G.removeArc(arc_removed);

        }
              
    }
    }

//to ensure that path and feasible and goes to each node only once 
public static void tryPath(Graph G) {
    if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
    }

    int numCitiesInPath = G.getN() + 1; // Include an extra element for the starting city
    int[] path = new int[numCitiesInPath]; // No need to include an extra element for the starting city

    try {
        int numCitiesEntered = numCitiesInPath;

        G.printNodes();
        
        System.out.println("\nEnter the " + numCitiesEntered + " cities in the route in order: ");
        for (int i = 0; i < numCitiesEntered; i++) {
            int cityNode = getInteger("City " +(i + 1) + ": ", 1, G.getN());
            path[i] = cityNode;
        }

        boolean isFeasible = G.checkPath(path);
        if (!isFeasible) {
            return;
        }

        // distance of path (call the function that calculates path cost) function  
        double pathCost = calculatePathCost(G, path);
        System.out.printf("\nThe total distance traveled is %.2f km.%n", pathCost);
        System.out.print("\n");
        
    } catch (NumberFormatException e) {
        System.out.println("ERROR: Invalid input! Please enter an integer.");
    }  
}

public static double calculatePathCost(Graph G, int[] path) {
    double totalDistance = 0.0;
    for (int i = 0; i < path.length - 1; i++) {
        int from = path[i];
        int to = path[i + 1];
        Node city1 = G.getNode(from);
        Node city2 = G.getNode(to);
        totalDistance += calculateDistance(city1, city2);
    }
    return totalDistance;
}


//calculating the distance of path (the equation)
public static double calculateDistance(Node city1, Node city2) {
    double lat1 = Math.toRadians(city1.getLat());
    double lon1 = Math.toRadians(city1.getLon());
    double lat2 = Math.toRadians(city2.getLat());
    double lon2 = Math.toRadians(city2.getLon());

    // Haversine formula
    double dlon = lon2 - lon1;
    double dlat = lat2 - lat1;
    double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    double radius = 6371; // Earth's radius in kilometers
    double distance = radius * c;

    return distance;
}

//reads user inputed integer 
public static int getInteger(String prompt, int LB, int UB) {
    while (true) {
        try {
            System.out.print(prompt);
            int value = Integer.parseInt(reader.readLine());
            if (value >= LB && value <= UB) {
                return value;
            }
            if (UB == Integer.MAX_VALUE) {
            	System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n", LB);
            	//System.out.print("\n");
            }
            

            else {

            	System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!\n");

            }
        } catch (IOException | NumberFormatException e) {
            if (UB == Integer.MAX_VALUE) {
            	System.out.printf("ERROR: Input must be an integer in [%d, infinity]!\n", LB);            	
            }
            else {
                System.out.println("ERROR: Input must be an integer in [" + LB + ", " + UB + "]!\n");

            }
        }
        
    }
}

//reads user inputed double 
public static double getDouble(String prompt, double LB, double UB) {
    while (true) {
        try {
            System.out.print(prompt);
            double value = Double.parseDouble(reader.readLine());
            if (value >= LB && value <= UB) {
                return value;             
            } 

            
            else {
                System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n", LB, UB);
                System.out.print("\n");
            }
            
        } catch (IOException | NumberFormatException e) {
            System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n", LB, UB);
            System.out.print("\n");
        }
    }
}

//when user wants to display nodes and arc table 
public static void displayGraph(Graph G) {
	if (G.getN() == 0) {
        System.out.println("\nERROR: No graph has been loaded!\n");
        return;
	}
	G.print();
   }
}
