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
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Stack<Integer> s = new Stack<Integer>();
    if (!graph.isEmpty()) {
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
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Queue<Integer> q = new LinkedList<Integer>();
    if (!graph.isEmpty()) {
      q.add(start);
      while (!q.isEmpty()) {
        int current = q.poll();
        if (!visited.contains(current)) {
          visited.add(current);
          for (int i = 0; i < graph.get(current).size(); i++) {
            q.add(graph.get(current).get(i));
          }
        }
      }
    }
    return visited;
  }

  // public static int findGirthList(HashMap<Integer, ArrayList<Integer>> graph) {
    
  // }
}
