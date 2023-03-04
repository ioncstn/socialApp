package graph;

import java.util.*;

public class WeightedGraph {
    private final Map<Integer, List<Pair<Integer, Integer>>> adjList;

    public WeightedGraph() {
        adjList = new HashMap<>();
    }
    public void addVertex(int vertex){
        adjList.putIfAbsent(vertex, new ArrayList<>());

    }
    public void addEdge(int v1, int v2, int weight){
        if(!adjList.get(v1).contains(new Pair<>(v2, weight))) {
            adjList.get(v1).add(new Pair<>(v2, weight));
            adjList.get(v2).add(new Pair<>(v1, weight));
        }
    }

    /***
     * method that searches for the longest path in the current graph from the given root using DFS.
     * @param root the vertex where the search starts
     * @return int - the length of the longest path from the given vertex
     */
    public Stack<Pair<Integer, Integer>> search(int root){
        int result = 0;
        Stack<Pair<Integer, Integer>> parentsStack = new Stack<>();
        HashMap<Integer, Integer> parent = new HashMap<>();
        Set<Integer> visited = new LinkedHashSet<>();
        Stack<Pair<Integer, Integer>> stack = new Stack<>();
        stack.push(new Pair<>(root, 0));
        parent.put(root, -1);
        visited.add(root);
        while(!stack.isEmpty()){
            var current = stack.pop();
            int currentVertex = current.first;
            int currentWeight = current.second;
            for(var currentAdj: adjList.get(currentVertex)){
                if(!visited.contains(currentAdj.first)){
                    visited.add(currentAdj.first);
                    parent.put(currentAdj.first, currentVertex);
                    stack.push(new Pair<>(currentAdj.first, currentWeight + 1));
                    if(result < currentWeight + 1){
                        result = currentWeight + 1;
                        Stack<Pair<Integer, Integer>> currentPath = new Stack<>();
                        int nowVert = currentAdj.first;
                        while(parent.get(nowVert) != -1){
                            currentPath.push(new Pair(nowVert, result));
                            nowVert = parent.get(nowVert);
                        }
                        currentPath.push(new Pair(nowVert, result));
                        parentsStack = (Stack<Pair<Integer, Integer>>) currentPath.clone();
                    }
                }
            }
        }
        return parentsStack;
    }

    public Map<Integer, List<Pair<Integer, Integer>>> getAdjList(){
        return adjList;
    }
}
