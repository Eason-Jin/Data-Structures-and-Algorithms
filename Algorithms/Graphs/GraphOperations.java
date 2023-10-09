package Graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GraphOperations {
  public static ArrayList<Integer> DfsList(HashMap<Integer, ArrayList<Integer>> graph) {
    ArrayList<Integer> visited = new ArrayList<Integer>();
    Stack<Integer> s = new Stack<Integer>();
    if (!graph.isEmpty()) {
      s.push(0);
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
}
