import java.util.*;

public class Solution {
    public int countCompleteComponents(int n, int[][] edges) {
        // Step 1: Build adjacency list
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n];
        int completeCount = 0;

        // Step 2: Find connected components using DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                dfs(i, visited, graph, component);

                // Step 3: Check if component is complete
                if (isComplete(component, graph)) {
                    completeCount++;
                }
            }
        }
        return completeCount;
    }

    private void dfs(int node, boolean[] visited, Map<Integer, Set<Integer>> graph, List<Integer> component) {
        // Use ArrayDeque for stack implementation
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(node);
        visited[node] = true;
        component.add(node);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    component.add(neighbor);
                    stack.push(neighbor);
                }
            }
        }
    }

    private boolean isComplete(List<Integer> component, Map<Integer, Set<Integer>> graph) {
        int size = component.size();
        int expectedEdges = (size * (size - 1)) / 2; // Complete graph should have this many edges
        int actualEdges = 0;
        
        for (int node : component) {
            actualEdges += graph.get(node).size();
        }
        actualEdges /= 2; // Each edge is counted twice in an undirected graph
        
        return actualEdges == expectedEdges;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Example test case
        int n = 6;
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}, {3, 4}};
        
        System.out.println(solution.countCompleteComponents(n, edges)); // Output: 3
    }
}
