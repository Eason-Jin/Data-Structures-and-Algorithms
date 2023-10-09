package Graphs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

class GraphConverter {
  /**
   * Takes input and converts a non weighted graph to an adjacency list
   *
   * <p>Input format: First line contains integer N denoting the number of vertices The following N
   * lines contain the edges, with the line number denoting the vertex number Terminated by N = 0
   *
   * @return
   * @throws IOException
   * @throws NumberFormatException
   */
  public static HashMap<Integer, ArrayList<Integer>> convertToAdjacencyList()
      throws NumberFormatException, IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int order = Integer.parseInt(br.readLine());

    if (order != 0) {
      HashMap<Integer, ArrayList<Integer>> graph = new HashMap<Integer, ArrayList<Integer>>();
      for (int i = 0; i < order; i++) {
        graph.put(i, new ArrayList<Integer>());
      }

      for (int i = 0; i < order; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());

        while (st.hasMoreTokens()) {
          graph.get(i).add(Integer.parseInt(st.nextToken()));
        }
        Collections.sort(graph.get(i));
      }
      return graph;
    } else {
      return null;
    }
  }

}
