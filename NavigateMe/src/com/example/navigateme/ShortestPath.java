package com.example.navigateme;

import java.util.ArrayList;
import java.util.List;

public class ShortestPath {
	private static final int NO_PARENT = -1;
	 
    public static List<Integer> path=new ArrayList<Integer>();
    private static void dijkstra(int[][] adjacencyMatrix,
                                         int startVertex,int endVertex)
    {
        int nVertices = adjacencyMatrix[0].length;
 
        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];
 
        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to 
        // i is finalized
        boolean[] added = new boolean[nVertices];
 
        // Initialize all distances as 
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
         
        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;
 
        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];
 
        // The starting vertex does not 
        // have a parent
        parents[startVertex] = NO_PARENT;
 
        // Find shortest path for all 
        // vertices
        for (int i = 1; i < nVertices; i++)
        {
 
            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is 
            // always equal to startNode in 
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++)
            {
                if (!added[vertexIndex] &&
                    shortestDistances[vertexIndex] < shortestDistance) 
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
 
            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;
 
            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                 
                if (edgeDistance > 0
                    && ((shortestDistance + edgeDistance) < 
                        shortestDistances[vertexIndex])) 
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }
 
        printSolution(startVertex, shortestDistances, parents,endVertex);
    }
 
    // A utility function to print 
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
                                      int[] distances,
                                        int[] parents,int endVertex)
    {
        int nVertices = distances.length;
        //System.out.print("Vertex\t Distance\tPath");
         
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) 
        {
            if (vertexIndex != startVertex && endVertex==vertexIndex) 
            {
                //System.out.print("\n" + startVertex + " -> ");
                //System.out.print(vertexIndex + " \t\t ");
                //System.out.print(distances[vertexIndex] + "\t\t");
                printPath(vertexIndex, parents,path);
            }
        }
        for(Integer x:path)
            System.out.println(x);
    }
 
    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex,
                                      int[] parents,List<Integer> path)
    {
        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents,path);
        path.add(currentVertex);
       // System.out.print(currentVertex + " ");
    }
 
    
    public static void process(int srcindex,int destindex)
    {
       //int srcindex=0,destindex=2;
       int graph[][] = new int[][]{{0, 2, 0, 0, 3, 0, 0, 0},
                                  {2, 0, 2, 2, 3, 2, 0, 0},
                                  {0, 2, 0, 3, 0, 0, 0, 0},
                                  {0, 2, 3, 0, 0, 0, 0, 0},
                                  {3, 3, 0, 0, 0, 2, 0, 0},
                                  {0, 2, 0, 0, 2, 0, 2, 0},
                                  {0, 0, 0, 0, 0, 2, 0, 2},
                                  {0, 0, 0, 0, 0, 0, 2, 0}
                                 
                                 };
        ShortestPath t = new ShortestPath();
        t.dijkstra(graph, srcindex,destindex);
    }
}

