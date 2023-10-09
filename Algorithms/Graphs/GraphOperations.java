package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class GraphOperations {
  /**
   * Performs a depth first search on a graph and returns the order of the vertices visited
   *
   * @param graph
   * @return
   */
  public static ArrayList<Integer> DfsList(HashMap<Integer, ArrayList<Integer>> graph, int start) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Stack<Integer> s = new Stack<Integer>();
    s.push(start);
    while (!s.isEmpty()) {
      int current = s.pop();
      if (!visited.contains(current)) {
        visited.add(current);
        for (int i = graph.get(current).size() - 1; i >= 0; i--) {
          s.push(graph.get(current).get(i));
        }
      }
    }
    return visited;
  }

  /**
   * Performs a breadth first search on a graph and returns the order of the vertices visited
   *
   * @param graph
   * @return
   */
  public static ArrayList<Integer> BfsList(HashMap<Integer, ArrayList<Integer>> graph, int start) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Queue<Integer> q = new LinkedList<Integer>();
    q.add(start);
    while (!q.isEmpty()) {
      int current = q.poll();
      if (!visited.contains(current)) {
        visited.add(current);
        for (int child : graph.get(current)) {
          q.add(child);
        }
      }
    }
    return visited;
  }

  /**
   * Finds the shortest cycle by performing BFS on every vertex
   *
   * @param graph
   * @param start
   * @param end
   * @return
   */
  public static int findGirthList(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return 0;
    }
    int girth = Integer.MAX_VALUE;
    int[] dist = new int[graph.size()];
    // Perform BFS from each vertex
    for (int v = 0; v < graph.size(); v++) {
      for (int a = 0; a < graph.size(); a++) {
        dist[a] = -1;
      }
      Queue<Integer> q = new LinkedList<Integer>();
      dist[v] = 0;
      q.add(v);
      while (!q.isEmpty()) {
        int current = q.poll();
        for (int child : graph.get(current)) {
          if (dist[child] == -1) {
            dist[child] = dist[current] + 1;
            q.add(child);
          } else if (child != current && dist[child] >= 0) {
            // Found a cycle (min length = 3), return its depth
            girth = Math.min(girth, dist[current] + dist[child] + 1);
          }
        }
      }
    }
    if (girth == Integer.MAX_VALUE) {
      return -1;
    } else {
      return girth;
    }
  }

  /**
   * Find topological ordering of a graph using DFS, basically the reverse of done time
   *
   * @param graph
   * @return
   */
  public static int[] findTopologicalOrderingDFS(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    Stack<Integer> order = new Stack<Integer>();
    // DFS then reverse done time
    Stack<Integer> stack = new Stack<>();
    int[] colour = new int[graph.size()];
    for (int s = 0; s < graph.size(); s++) {
      if (colour[s] == 0) {
        stack.push(s);
        while (!stack.isEmpty()) {
          int u = stack.peek();
          // If the vertex is unvisited
          if (colour[u] == 0) {
            colour[u] = 1;
          }
          boolean allChildrenVisited = true;
          ArrayList<Integer> children = graph.get(u);
          for (int v : children) {
            if (colour[v] == 0) {
              stack.push(v);
              allChildrenVisited = false;
              break; // Stop looking for other children once you find an unvisited child
            }
          }

          if (allChildrenVisited) {
            colour[u] = 2; // Mark vertex as visited
            order.push(stack.pop()); // Remove it from the stack
          }
        }
      }
    }
    int[] orderArray = new int[order.size()];
    for (int i = 0; i < orderArray.length; i++) {
      orderArray[i] = order.pop();
    }
    return orderArray;
  }
}
