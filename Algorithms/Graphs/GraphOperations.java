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
        for (int child : graph.get(current)) {
          s.push(child);
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

  public static int findGirthList(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return 0;
    }
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
            // Found a cycle, return its depth
            return dist[current] + dist[child] + 1;
          }
        }
      }
    }
    return 0;
  }
}
