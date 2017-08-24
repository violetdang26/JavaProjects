import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class Dijkstra {

  // Keep a fast index to nodes in the map
  private Map<String, Vertex> vertexNames;

  /**
   * Construct an empty Dijkstra with a map. The map's key is the name of a vertex
   * and the map's value is the vertex object.
   */
  public Dijkstra() {
    vertexNames = new HashMap<String, Vertex>();
  }

  /**
   * Adds a vertex to the dijkstra. Throws IllegalArgumentException if two vertices
   * with the same name are added.
   * 
   * @param v
   *          (Vertex) vertex to be added to the dijkstra
   */
  public void addVertex(Vertex v) {
    if (vertexNames.containsKey(v.name))
      throw new IllegalArgumentException("Cannot create new vertex with existing name.");
    vertexNames.put(v.name, v);
  }

  /**
   * Gets a collection of all the vertices in the dijkstra
   * 
   * @return (Collection<Vertex>) collection of all the vertices in the dijkstra
   */
  public Collection<Vertex> getVertices() {
    return vertexNames.values();
  }

  /**
   * Gets the vertex object with the given name
   * 
   * @param name
   *          (String) name of the vertex object requested
   * @return (Vertex) vertex object associated with the name
   */
  public Vertex getVertex(String name) {
    return vertexNames.get(name);
  }

  /**
   * Adds a directed edge from vertex u to vertex v
   * 
   * @param nameU
   *          (String) name of vertex u
   * @param nameV
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addEdge(String nameU, String nameV, Double cost) {
    if (!vertexNames.containsKey(nameU))
      throw new IllegalArgumentException(nameU + " does not exist. Cannot create edge.");
    if (!vertexNames.containsKey(nameV))
      throw new IllegalArgumentException(nameV + " does not exist. Cannot create edge.");
    Vertex sourceVertex = vertexNames.get(nameU);
    Vertex targetVertex = vertexNames.get(nameV);
    Edge newEdge = new Edge(sourceVertex, targetVertex, cost);
    sourceVertex.addEdge(newEdge);
  }

  /**
   * Adds an undirected edge between vertex u and vertex v by adding a directed
   * edge from u to v, then a directed edge from v to u
   * 
   * @param nameU
   *          (String) name of vertex u
   * @param nameV
   *          (String) name of vertex v
   * @param cost
   *          (double) cost of the edge between vertex u and v
   */
  public void addUndirectedEdge(String nameU, String nameV, double cost) {
    addEdge(nameU, nameV, cost);
    addEdge(nameV, nameU, cost);
  }

  // STUDENT CODE STARTS HERE

  /**
   * Computes the euclidean distance between two points as described by their
   * coordinates
   * 
   * @param ux
   *          (double) x coordinate of point u
   * @param uy
   *          (double) y coordinate of point u
   * @param vx
   *          (double) x coordinate of point v
   * @param vy
   *          (double) y coordinate of point v
   * @return (double) distance between the two points
   */
  public double computeEuclideanDistance(double ux, double uy, double vx, double vy) {
        // TODO
        return Math.sqrt(Math.pow(ux-vx, 2)+Math.pow(uy- vy,2));
  }

  /**
   * Calculates the euclidean distance for all edges in the map using the
   * computeEuclideanCost method.
   */
  public void computeAllEuclideanDistances() {
        for(Vertex u : getVertices()) {
        	for (Edge e: u.adjacentEdges) {
        		Vertex v = e.target;
        		e.distance = computeEuclideanDistance(u.x,u.y,v.x,v.y);
        	}
        }
  }

  /**
   * Dijkstra's Algorithm. 
   * 
   * @param s
   *          (String) starting city name
   */
  public void doDijkstra(String s) {
        // TODO 
	  ArrayList<Vertex> vList = new ArrayList<Vertex>(); 

	  for (Vertex v : getVertices()) { //set all vertexes distance to pos. inf
		  v.distance=Double.POSITIVE_INFINITY;
	  }
	  
	  Vertex s_vertex = getVertex(s); //get vertex s to set its values
	  s_vertex.known=true; 
	  s_vertex.distance=0;
	  
	  for(Edge e : s_vertex.adjacentEdges) {
		  Vertex target = e.target;
		  target.prev = s_vertex;
		  target.distance=e.distance; //set distance of the adjacent vertices to the path distance
	  }
	  
	  //add all other cities to the list
	  for (Vertex v : getVertices()) { //list of unknown vertices
		  //get vertex V in each city
		  if (!v.equals(s_vertex)) {
			  vList.add(v);
		  }
	  }
	  
	  //calculate the minimum distance of the list
	  
	  while(!vList.isEmpty()) { //search min
		  int minIndex=0;
		  
		  for(int i = 0;i<vList.size();i++) {
			  double dist_curr = vList.get(i).distance;
			  double min_dist = vList.get(minIndex).distance;
			  
			  if(dist_curr < min_dist) {
				  minIndex = i;
			  }
		  }
		  
		  Vertex minVertex = vList.get(minIndex); //get the vertex of the minimum index
		  vList.remove(minIndex);
		  minVertex.known = true;
		  
		  for (Edge e:minVertex.adjacentEdges) { //check edges of the minimum vertex
			 Vertex target = e.target;
			 if(minVertex.distance+e.distance<target.distance) {
				 target.distance=minVertex.distance+e.distance;
				 target.prev=minVertex;
			 }
		  }
	  }
  }


/**
   * Returns a list of edges for a path from city s to city t. This will be the
   * shortest path from s to t as prescribed by Dijkstra's algorithm
   * 
   * @param s
   *          (String) starting city name
   * @param t
   *          (String) ending city name
   * @return (List<Edge>) list of edges from s to t
   */

  public List<Edge> getDijkstraPath(String s, String t) {
		//method check if the previous vertex is null, if not, add edge to list
	  	doDijkstra(s);
	  	
	    List<Edge> path = new LinkedList<Edge>(); 
	    
	    //initiate tracker to the target vertex
	    Vertex tracker = getVertex(t); // set target
	    
	    
	    while (tracker.prev != null){
	    	//add edge to path list
	    	path.add(new Edge(tracker.prev, tracker, tracker.distance));
	    	tracker = tracker.prev; 
	    }
	    return path;
	    
	}
    
  

  // STUDENT CODE ENDS HERE

  /**
   * Prints out the adjacency list of the dijkstra for debugging
   */
  public void printAdjacencyList() {
    for (String u : vertexNames.keySet()) {
      StringBuilder sb = new StringBuilder();
      sb.append(u);
      sb.append(" -> [ ");
      for (Edge e : vertexNames.get(u).adjacentEdges) {
        sb.append(e.target.name);
        sb.append("(");
        sb.append(e.distance);
        sb.append(") ");
      }
      sb.append("]");
      System.out.println(sb.toString());
    }
  }


  /** 
   * A main method that illustrates how the GUI uses Dijkstra.java to 
   * read a map and represent it as a graph. 
   * You can modify this method to test your code on the command line. 
   */
  public static void main(String[] argv) throws IOException {
    String vertexFile = "cityxy.txt"; 
    String edgeFile = "citypairs.txt";
	  
	//String vertexFile = "/Users/hoadang/Desktop/Columbia/Spring 2017/Data Structure/HW5/src/cityxy.txt"; 
	//String edgeFile = "/Users/hoadang/Desktop/Columbia/Spring 2017/Data Structure/HW5/src/citypairs.txt";

    Dijkstra dijkstra = new Dijkstra();
    String line;

    // Read in the vertices
    BufferedReader vertexFileBr = new BufferedReader(new FileReader(vertexFile));
    while ((line = vertexFileBr.readLine()) != null) {
      String[] parts = line.split(",");
      if (parts.length != 3) {
        vertexFileBr.close();
        throw new IOException("Invalid line in vertex file " + line);
      }
      String cityname = parts[0];
      int x = Integer.valueOf(parts[1]);
      int y = Integer.valueOf(parts[2]);
      Vertex vertex = new Vertex(cityname, x, y);
      dijkstra.addVertex(vertex);
    }
    vertexFileBr.close();

    BufferedReader edgeFileBr = new BufferedReader(new FileReader(edgeFile));
    while ((line = edgeFileBr.readLine()) != null) {
      String[] parts = line.split(",");
      if (parts.length != 3) {
        edgeFileBr.close();
        throw new IOException("Invalid line in edge file " + line);
      }
      dijkstra.addUndirectedEdge(parts[0], parts[1], Double.parseDouble(parts[2]));
    }
    edgeFileBr.close();

    // Compute distances. 
    // This is what happens when you click on the "Compute All Euclidean Distances" button.
    dijkstra.computeAllEuclideanDistances();
    
    // print out an adjacency list representation of the graph
    dijkstra.printAdjacencyList();

    // This is what happens when you click on the "Draw Dijkstra's Path" button.

    // In the GUI, these are set through the drop-down menus.
    String startCity = "Montreal";
    String endCity = "Pittsburgh";
    //System.out.print(endCity);
    // Get weighted shortest path between start and end city. 
    List<Edge> path = dijkstra.getDijkstraPath(startCity, endCity);


    
    System.out.println("Shortest path between "+startCity+" and "+endCity+": ");
    System.out.println(path);

    for (int i=path.size()-1;i>-1;i--) {
    	Edge e = path.get(i);
    	System.out.print(e.source.name.toString()+ " -> ");
    }
    System.out.print(endCity);
    
    System.out.print(" Distance: "+path.get(0).distance);
    
    
  }

}