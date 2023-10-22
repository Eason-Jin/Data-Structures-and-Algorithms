package Graphs;

import DataStructures.DisjointSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class GraphOperations {
  /**
   * Performs a depth first search on a graph and returns the order of the vertices visited
   *
   * @param graph
   * @return
   */
  public static ArrayList<Integer> Dfs(HashMap<Integer, ArrayList<Integer>> graph, int start) {
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
  public static ArrayList<Integer> Bfs(HashMap<Integer, ArrayList<Integer>> graph, int start) {
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
  public static int findGirth(HashMap<Integer, ArrayList<Integer>> graph) {
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
  public static int findIndegree(HashMap<Integer, ArrayList<Integer>> graph, int vertex) {
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
      indegrees[i] = findIndegree(graph, i);
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
  public static HashMap<Integer, ArrayList<Integer>> reverseGraph(
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

  /**
   * Find the strongly connected components of a graph
   *
   * @param graph
   * @return
   */
  public static ArrayList<ArrayList<Integer>> findStronglyConnectedComponents(
      HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return null;
    }
    ArrayList<ArrayList<Integer>> components = new ArrayList<ArrayList<Integer>>();
    HashMap<Integer, ArrayList<Integer>> reversed = reverseGraph(graph);
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

  /**
   * Find the index of the maximum value in an array
   *
   * @param array
   * @return
   */
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

  /**
   * Find the maximum matching of a bipartite graph
   *
   * @param graph
   * @return
   */
  public static ArrayList<int[]> maximalMatching(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty() || !isBipartite(graph)) {
      return null;
    }
    // Simple greedy algorithm
    // Iterate through each vertex and add it to the matching if it is not already matched
    ArrayList<int[]> matching = new ArrayList<int[]>();
    Set<Integer> used = new HashSet<Integer>();
    for (int i = 0; i < graph.size(); i++) {
      for (int j = 0; j < graph.get(i).size(); j++) {
        int[] edge = {i, graph.get(i).get(j)};
        if (!used.contains(i) && !used.contains(graph.get(i).get(j))) {
          Arrays.sort(edge);
          matching.add(edge);
          used.add(i);
          used.add(graph.get(i).get(j));
        }
      }
    }
    return matching;
  }

  /**
   * Check if a graph is bipartite
   *
   * @param graph
   * @return
   */
  public static boolean isBipartite(HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty()) {
      return false;
    }
    int[] set = new int[graph.size()]; // 0 = unassigned, 1 = set 1, 2 = set 2
    LinkedList<Integer> queue = new LinkedList<Integer>();
    // BFS to assign vertices to sets
    for (int s = 0; s < graph.size(); s++) {
      if (set[s] != 0) continue; // Skip vertices already assigned to a set
      queue.add(s);
      set[s] = 1; // Assign the first vertex to set 1
      while (!queue.isEmpty()) {
        int u = queue.poll();
        int currentSet = set[u];
        int nextSet = (currentSet == 1) ? 2 : 1; // Alternate between sets 1 and 2
        for (int v : graph.get(u)) {
          if (set[v] == 0) {
            set[v] = nextSet;
            queue.add(v);
          } else if (set[v] != nextSet) {
            return false; // Conflict, not bipartite
          }
        }
      }
    }
    return true;
  }

  /**
   * Check if a graph is cyclic
   *
   * @param graph
   * @return
   */
  public static boolean isCyclic(HashMap<Integer, ArrayList<Integer>> graph) {
    Stack<Integer> stack = new Stack<>();
    int[] colour = new int[graph.size()];
    // DFS to detect back-arcs
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
            if (colour[v] == 1) {
              return true; // Back-arc detected, graph has a cycle
            } else if (colour[v] == 0) {
              stack.push(v);
              allChildrenVisited = false;
              break; // Stop looking for other children once you find an unvisited child
            }
          }
          if (allChildrenVisited) {
            colour[u] = 2; // Mark vertex as visited
            stack.pop(); // Remove it from the stack
          }
        }
      }
    }
    return false;
  }

  /**
   * Find the all the augmented paths in a bipartite graph
   *
   * @param graph
   * @return
   */
  public static ArrayList<ArrayList<Integer>> findAugmentingPaths(
      HashMap<Integer, ArrayList<Integer>> graph) {
    if (graph == null || graph.isEmpty() || !isBipartite(graph)) {
      return null;
    }
    ArrayList<ArrayList<Integer>> augmentingPaths = new ArrayList<>();
    ArrayList<int[]> matching = maximalMatching(graph);
    ArrayList<Integer> unmatched = findUnmatchedVertices(graph, matching);
    // Find augmenting paths starting from unmatched vertices
    for (int start : unmatched) {
      // Perform BFS to find an augmenting path starting from each unmatched vertex
      Queue<Integer> queue = new LinkedList<>();
      boolean[] visited = new boolean[graph.size()];
      int[] parent = new int[graph.size()];
      ArrayList<Integer> path = new ArrayList<>();
      queue.add(start);
      visited[start] = true;
      while (!queue.isEmpty()) {
        int current = queue.poll();
        for (int child : graph.get(current)) {
          if (!visited[child]) {
            visited[child] = true;
            parent[child] = current;
            queue.add(child);
            if (isMatchingEdge(matching, current, child)) {
              if (!visited[child]) {
                visited[child] = true;
                parent[child] = current;
                queue.add(child);
              }
            } else {
              // Add to the path but continue searching
              while (child != start) {
                path.add(child);
                child = parent[child];
              }
              path.add(start); // Add the start vertex to complete the path
              if (path.size() > 2
                  && unmatched.contains(path.get(0))
                  && unmatched.contains(path.get(path.size() - 1))) {
                augmentingPaths.add(new ArrayList<>(path));
              }
              path.clear(); // Clear the path for the next augmenting path
            }
          }
        }
      }
    }

    return augmentingPaths;
  }

  /**
   * Check if an edge is in a matching
   *
   * @param matching
   * @param u
   * @param v
   * @return
   */
  private static boolean isMatchingEdge(ArrayList<int[]> matching, int u, int v) {
    for (int[] edge : matching) {
      int first = edge[0];
      int second = edge[1];

      if ((first == u && second == v) || (first == v && second == u)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Find the unmatched vertices in a bipartite graph
   *
   * @param graph
   * @param matching
   * @return
   */
  private static ArrayList<Integer> findUnmatchedVertices(
      HashMap<Integer, ArrayList<Integer>> graph, ArrayList<int[]> matching) {
    ArrayList<Integer> unmatched = new ArrayList<Integer>();
    for (int i = 0; i < graph.size(); i++) {
      unmatched.add(i);
    }
    for (int[] edge : matching) {
      unmatched.remove((Integer) edge[0]);
      unmatched.remove((Integer) edge[1]);
    }
    return unmatched;
  }

  /**
   * Find the degrees of separation of a graph
   *
   * @param graph
   * @return
   */
  public static int findDegreesOfSeparation(HashMap<Integer, ArrayList<Integer>> graph) {
    // Perform BFS at every vertex
    int max = 0;
    for (int vertex = 0; vertex < graph.size(); vertex++) {
      int[] dist = new int[graph.size()];
      Queue<Integer> queue = new LinkedList<Integer>();
      queue.add(vertex);
      while (!queue.isEmpty()) {
        int current = queue.poll();
        for (int child : graph.get(current)) {
          if (dist[child] == 0) {
            dist[child] = dist[current] + 1;
            queue.add(child);
          }
        }
      }

      for (int i = 0; i < graph.size(); i++) {
        if (dist[i] > max) {
          max = dist[i];
        }
      }
    }
    return max;
  }

  /**
   * Find the shortest path between two vertices in a graph with no negative weights using
   * Dijsktra's algorithm
   *
   * @param graph
   * @return
   */
  public static int[][] dijkstra(HashMap<Integer, ArrayList<int[]>> graph) {
    int[][] dist = initDist(graph.size());
    // Perform BFS from each vertex
    for (int i = 0; i < graph.size(); i++) {
      Queue<Integer> queue = new LinkedList<Integer>();
      queue.add(i);
      while (!queue.isEmpty()) {
        int current = queue.poll();
        for (int[] child : graph.get(current)) {
          int vertex = child[0];
          int weight = child[1];
          // Update the distance if it is shorter than what is already stored
          if (dist[i][vertex] > dist[i][current] + weight) {
            dist[i][vertex] = dist[i][current] + weight;
            queue.add(vertex);
          }
        }
      }
    }
    return dist;
  }

  /**
   * Find the shortest path between two vertices in a graph with no negative cycles using Bellman
   * Ford's algorithm
   *
   * @param graph
   * @param source
   * @return
   */
  public static int[] bellmanFordOneVertex(HashMap<Integer, ArrayList<int[]>> graph, int source) {
    int[] dist = new int[graph.size()];
    // Initialize distances to infinity
    for (int i = 0; i < graph.size(); i++) {
      dist[i] = Integer.MAX_VALUE;
    }
    // The distance to the source vertex is 0
    dist[source] = 0;
    // Check all edges
    for (int i = 0; i < graph.size() - 1; i++) {
      for (int v = 0; v < graph.size(); v++) {
        for (int[] child : graph.get(v)) {
          int vertex = child[0];
          int weight = child[1];
          if (dist[vertex] > dist[v] + weight) {
            dist[vertex] = dist[v] + weight;
          }
        }
      }
    }
    // Check for negative-weight cycles
    for (int v = 0; v < graph.size(); v++) {
      for (int[] child : graph.get(v)) {
        int vertex = child[0];
        int weight = child[1];
        if (dist[vertex] > dist[v] + weight) {
          throw new RuntimeException("Negative-weight cycle detected");
        }
      }
    }
    return dist;
  }

  /**
   * Find the shortest path for all vertices in a graph with no negative cycles using Bellman Ford's
   * algorithm
   *
   * @param graph
   * @return
   */
  public static int[][] bellmanFord(HashMap<Integer, ArrayList<int[]>> graph) {
    int[][] dist = new int[graph.size()][graph.size()];
    for (int v = 0; v < graph.size(); v++) {
      dist[v] = bellmanFordOneVertex(graph, v);
    }
    return dist;
  }

  /**
   * Find the all shortest paths between two vertices in a graph using Floyd Warshall's algorithm
   *
   * @param graph
   * @return
   */
  public static int[][] floydWarshall(HashMap<Integer, ArrayList<int[]>> graph) {
    int[][] dist = initDist(graph.size());
    // Initialise the distances
    for (int i = 0; i < graph.size(); i++) {
      for (int[] child : graph.get(i)) {
        int vertex = child[0];
        int weight = child[1];
        dist[i][vertex] = weight;
      }
    }
    // Perform the algorithm
    for (int intermediate = 0; intermediate < graph.size(); intermediate++) {
      for (int source = 0; source < graph.size(); source++) {
        for (int destination = 0; destination < graph.size(); destination++) {
          // If the intermediate vertex is reachable from both the source and destination
          if (dist[source][intermediate] != Integer.MAX_VALUE
              && dist[intermediate][destination] != Integer.MAX_VALUE) {
            // Compare direct path with indirect path through the intermediate vertex
            dist[source][destination] =
                Math.min(
                    dist[source][destination],
                    dist[source][intermediate] + dist[intermediate][destination]);
          }
        }
      }
    }
    return dist;
  }

  /**
   * Initialise the distance matrix
   *
   * @param size
   * @return
   */
  private static int[][] initDist(int size) {
    int[][] dist = new int[size][size];
    // Initialise all values to infinity
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        dist[i][j] = Integer.MAX_VALUE;
      }
    }
    // A vertex to itself is 0
    for (int i = 0; i < size; i++) {
      dist[i][i] = 0;
    }
    return dist;
  }

  /**
   * Find the minimum spanning tree of a graph using Prim's algorithm
   *
   * @param graph
   * @return
   */
  public static Pair<int[], Integer> minimumSpanningTreePrim(
      HashMap<Integer, ArrayList<int[]>> graph) {
    int n = graph.size();
    int[] pred = new int[n];
    int[] weight = new int[n];
    boolean[] visited = new boolean[n];

    for (int i = 0; i < n; i++) {
      weight[i] = Integer.MAX_VALUE;
      visited[i] = false;
      pred[i] = -1;
    }

    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]); // Sort by weight
    pq.offer(new int[] {0, 0}); // Start with vertex 0

    while (!pq.isEmpty()) {
      int[] current = pq.poll();
      int u = current[0];
      visited[u] = true;
      // PFS
      for (int[] child : graph.get(u)) {
        int currentVertex = child[0];
        int currentWeight = child[1];
        // If the vertex is unvisited and the weight is less than the current weight
        if (!visited[currentVertex] && currentWeight < weight[currentVertex]) {
          // Update the weight and predecessor
          pred[currentVertex] = u;
          weight[currentVertex] = currentWeight;
          // Add the vertex to the queue
          pq.offer(new int[] {currentVertex, weight[currentVertex]});
        }
      }
    }

    int totalWeight = 0;
    for (int w : weight) {
      if (w != Integer.MAX_VALUE) {
        totalWeight += w;
      }
    }

    return new Pair<int[], Integer>(pred, totalWeight);
  }

  /**
   * Find the minimum spanning tree of a graph using Kruskal's algorithm
   *
   * @param graph
   * @return
   */
  public static Pair<DisjointSet, Integer> minimumSpanningTreeKruskal(
      HashMap<Integer, ArrayList<int[]>> graph) {
    // Initialize disjoint set for each vertex in its own set
    DisjointSet disjointSet = new DisjointSet(graph.size());

    // Sort the edges in increasing order of weight
    ArrayList<int[]> edges = new ArrayList<>();
    for (Integer current : graph.keySet()) {
      for (int[] child : graph.get(current)) {
        int vertex = child[0];
        int weight = child[1];
        edges.add(new int[] {current, vertex, weight});
      }
    }
    Collections.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

    int totalWeight = 0; // Initialize the total weight to zero

    // Process each edge in increasing order of cost
    for (int[] edge : edges) {
      int u = edge[0];
      int v = edge[1];

      // If u and v are not in the same set, add the edge to the MST
      if (disjointSet.findParent(u) != disjointSet.findParent(v)) {
        disjointSet.union(u, v);
        totalWeight += edge[2]; // Add the weight of the edge to the total weight
      }
    }

    return new Pair<DisjointSet, Integer>(disjointSet, totalWeight);
  }
}
