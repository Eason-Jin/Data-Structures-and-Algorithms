package Graphs;

import java.util.ArrayList;
import java.util.Collections;
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

  /**
   * Find the indegree of a vertex in a list graph
   *
   * @param graph
   * @param vertex
   * @return
   */
  public static int findIndegreeList(HashMap<Integer, ArrayList<Integer>> graph, int vertex) {
    int degree = 0;
    for (int i = 0; i < graph.size(); i++) {
      if (graph.get(i).contains(vertex)) {
        degree++;
      }
    }
    return degree;
  }

  /**
   * Find topological ordering of a graph using indegree
   *
   * @param graph
   * @return
   */
  public static int[] findTopologicalOrderingIndegree(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    // Find indegrees
    int[] indegrees = new int[graph.size()];
    for (int i = 0; i < graph.size(); i++) {
      indegrees[i] = findIndegreeList(graph, i);
    }
    int[] order = new int[graph.size()];
    Queue<Integer> q = new LinkedList<Integer>();
    for (int j = 0; j < graph.size(); j++) {
      if (indegrees[j] == 0) {
        q.add(j);
      }
    }
    int count = 0;
    while (!q.isEmpty()) {
      int current = q.poll();
      order[count] = current;
      count++;
      for (int child : graph.get(current)) {
        indegrees[child]--; // Remove the vertex from the graph
        if (indegrees[child] == 0) {
          q.add(child);
        }
      }
    }
    return order;
  }

  /**
   * Find the reverse of a list graph
   *
   * @param graph
   * @return
   */
  public static HashMap<Integer, ArrayList<Integer>> reverseGraphList(
      HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    HashMap<Integer, ArrayList<Integer>> reversed = new HashMap<Integer, ArrayList<Integer>>();
    for (int i = 0; i < graph.size(); i++) {
      for (int j = 0; j < graph.get(i).size(); j++) {
        if (!reversed.containsKey(graph.get(i).get(j))) {
          reversed.put(graph.get(i).get(j), new ArrayList<Integer>());
        }
        reversed.get(graph.get(i).get(j)).add(i);
      }
    }
    return reversed;
  }

  public static ArrayList<ArrayList<Integer>> findStronglyConnectedComponents(
      HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();
    HashMap<Integer, ArrayList<Integer>> reversed = reverseGraphList(graph);
    // DFS on reversed graph
    Stack<Integer> stack = new Stack<>();
    int[] colour = new int[graph.size()];
    int[] finish = new int[graph.size()];
    int time = 0;
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
          ArrayList<Integer> children = reversed.get(u);
          for (int child : children) {
            if (colour[child] == 0) {
              stack.push(child);
              allChildrenVisited = false;
              break; // Stop looking for other children once you find an unvisited child
            }
          }

          if (allChildrenVisited) {
            colour[u] = 2; // Mark vertex as visited
            stack.pop();
            finish[u] = time; // Remove it from the stack
            time++;
          }
        }
      }
    }
    // DFS on original graph
    ArrayList<Integer> visited = new ArrayList<Integer>();
    for (int i = 0; i < graph.size(); i++) {
      int v = findMaxIndex(finish);
      finish[v] = -1;
      if (!visited.contains(v)) {
        ArrayList<Integer> component = new ArrayList<Integer>();
        Stack<Integer> s = new Stack<Integer>();
        s.push(v);
        while (!s.isEmpty()) {
          int current = s.pop();
          if (!visited.contains(current)) {
            visited.add(current);
            component.add(current);
            for (int child : graph.get(current)) {
              s.push(child);
            }
          }
        }
        Collections.sort(component);
        components.add(component);
      }
    }
    return components;
  }

  private static int findMaxIndex(int[] array) {
    int max = Integer.MIN_VALUE;
    int maxIndex = -1;
    for (int i = 0; i < array.length; i++) {
      if (array[i] > max) {
        maxIndex = i;
        max = array[i];
      }
    }
    return maxIndex;
  }
}
