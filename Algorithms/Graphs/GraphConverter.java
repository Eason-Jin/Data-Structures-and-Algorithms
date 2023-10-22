package Graphs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
  public static HashMap<Integer, ArrayList<Integer>> convertToAdjacencyList(boolean readFile)
      throws NumberFormatException, IOException {
        BufferedReader br;
        if (readFile) {
            br = new BufferedReader(new FileReader("test.in"));
        } else {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
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

  /**
   * Takes input and converts a graph to an adjacency matrix
   *
   * <p>Input format: First line contains integer N denoting the number of vertices The following N
   * lines contain the edges, with the line number denoting the vertex number Terminated by N = 0
   *
   * @return
   * @throws NumberFormatException
   * @throws IOException
   */
  public static int[][] convertToAdjacencyMatrix(boolean isWeighted, boolean readFile) throws NumberFormatException, IOException {
    BufferedReader br;
    if (readFile) {
        br = new BufferedReader(new FileReader("test.in"));
    } else {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    int order = Integer.parseInt(br.readLine());

    if (order != 0) {
      int[][] graph = new int[order][order];
      for (int i = 0; i < order; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());
        while (st.hasMoreTokens()) {
          graph[i][Integer.parseInt(st.nextToken())] = isWeighted? Integer.parseInt(st.nextToken()) : 1;
        }
      }
      return graph;
    } else {
      return null;
    }
  }

  /**
   * Takes input and converts a weighted graph to an adjacency list
   *
   * <p>Input format: First line contains integer N denoting the number of vertices The following N
   * lines contain the edges, with the line number denoting the vertex number Terminated by N = 0
   *
   * @return
   * @throws IOException
   * @throws NumberFormatException
   */
  public static HashMap<Integer, ArrayList<int[]>> convertToWeightedAdjacencyList(boolean readFile)
      throws NumberFormatException, IOException {
    BufferedReader br;
    if (readFile) {
        br = new BufferedReader(new FileReader("test.in"));
    } else {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    int order = Integer.parseInt(br.readLine());

    if (order != 0) {
      HashMap<Integer, ArrayList<int[]>> graph = new HashMap<Integer, ArrayList<int[]>>();
      for (int i = 0; i < order; i++) {
        graph.put(i, new ArrayList<int[]>());
      }

      for (int i = 0; i < order; i++) {
        StringTokenizer st = new StringTokenizer(br.readLine());

        while (st.hasMoreTokens()) {
          int[] edge = new int[2];
          edge[0] = Integer.parseInt(st.nextToken()); // vertex
          edge[1] = Integer.parseInt(st.nextToken()); // weight
          graph.get(i).add(edge);
        }
        Collections.sort(graph.get(i), new ArrayComparator<Integer>());
      }
      return graph;
    } else {
      return null;
    }
  }

  /** Class to compare edges in weighted graphs */
  static class ArrayComparator<T extends Comparable<T>> implements Comparator<int[]> {

    @Override
    public int compare(int[] o1, int[] o2) {
      // Compare the first element of the list only
      return Integer.compare(o1[0], o2[0]);
    }
  }

  
}
