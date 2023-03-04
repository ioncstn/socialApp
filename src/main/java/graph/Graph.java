package graph;

import java.util.*;

public class Graph {
    private final Map<Integer, List<Integer>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    /***
     * method that adds a vertex to the graph.
     * @param value the value of the vertex
     */
    public void addVertex(int value){
        adjList.putIfAbsent(value, new ArrayList<>());
    }

    /***
     * method that adds an edge to the graph.
     * @param v1 the value of the first vertex
     * @param v2 the value of the second vertex
     */
    public void addEdge(int v1, int v2){
        adjList.get(v1).add(v2);
        adjList.get(v2).add(v1);
    }

    /***
     * method that returns the number of connected components in the graph.
     * @return int - the number of connected components
     */
    public int connectedComponents(){
        Set<Integer> visited = new LinkedHashSet<Integer>();
        int result = 0;
        for (int v : adjList.keySet()) {
                if (!visited.contains(v)) {
                    visited.addAll(BFS(v));
                    result++;
                }
            }
        return result;
    }

    /***
     * method that does a BFS on the graph and returns a set containing the visited vertices.
     * @param root where the BFS begins
     * @return Set - the visited set
     */
    private Set<Integer> BFS(int root) {
        Set<Integer> visited = new LinkedHashSet<Integer>();
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(root);
        visited.add(root);
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int v : adjList.get(vertex)) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    queue.add(v);
                }
            }
        }
        return visited;
    }

    /***
     * method that creates a WeightedGraph from the current graph by doing a BFS.
     * @param root - where the BFS begins
     * @return WeightedGraph - the weighted graph resulted from the current graph
     */
    private WeightedGraph create(int root){
        Set<Integer> visited = new LinkedHashSet<Integer>();
        Queue<Pair<Integer, Integer>> queue = new LinkedList<Pair<Integer, Integer>>();
        queue.add(new Pair<>(root, 0));
        visited.add(root);
        WeightedGraph resultGraph = new WeightedGraph();
        while(!queue.isEmpty()){
            var current = queue.poll();
            int currentVertex = current.first;
            int currentWeight = current.second;
            for(int adjVertex: adjList.get(currentVertex)){
                if(!visited.contains(adjVertex)){
                    resultGraph.addVertex(currentVertex);
                    resultGraph.addVertex(adjVertex);
                    resultGraph.addEdge(currentVertex, adjVertex, currentWeight + 1);
                    resultGraph.addEdge(adjVertex, currentVertex, currentWeight + 1);
                    visited.add(adjVertex);
                    queue.add(new Pair<>(adjVertex, currentWeight + 1));
                }
            }
        }
        return resultGraph;
    }

    /***
     * method that finds an approximation of the longest path in the current graph.
     * @return int - the length of the longest path
     */
    public Stack<Pair<Integer, Integer>> longestPath(){
        int maxLen = 0;
        Stack<Pair<Integer, Integer>> parents = new Stack<>();
        for(int vertex: adjList.keySet()){
            WeightedGraph weightedGraph = create(vertex);
            for(int otherVertex: weightedGraph.getAdjList().keySet()){
                if(vertex != otherVertex){
                    var tempP = weightedGraph.search(otherVertex);
                    if(!tempP.isEmpty()) {
                        var current = tempP.pop();
                        if (maxLen < current.second) {
                            maxLen = current.second;
                            tempP.push(current);
                            parents = tempP;
                        }
                    }
                }
            }
        }
        return parents;
    }
}
