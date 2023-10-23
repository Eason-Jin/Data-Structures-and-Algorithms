package DataStructures;

import java.util.ArrayList;
import java.util.Collections;

public class DisjointSet {
  private int[] parent;
  private int[] height;

  public DisjointSet(int n) {
    parent = new int[n];
    height = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
      height[i] = 0;
    }
  }

  public int findParent(int x) {
    return parent[x];
  }

  public int findRoot(int x) {
    int root = x;
    while (root != parent[root]) {
      root = parent[root];
    }
    return root;
  }

  public boolean isSameSet(int x, int y) {
    return findRoot(x) == findRoot(y);
  }

  public void union(int x, int y) {
    int rootX = findRoot(x);
    int rootY = findRoot(y);

    if (rootX == rootY) {
      return; // x and y are already in the same set
    }

    // Optimised join
    if (height[rootX] < height[rootY]) {
      parent[rootX] = rootY;
    } else if (height[rootX] > height[rootY]) {
      parent[rootY] = rootX;
    } else {
      parent[rootY] = rootX;
      height[rootX]++;
    }
  }

  private ArrayList<Integer> findRoots() {
    ArrayList<Integer> roots = new ArrayList<Integer>();
    for (int i = 0; i < parent.length; i++) {
      if (parent[i] == i) {
        roots.add(i);
      }
    }
    return roots;
  }

  private ArrayList<Integer> findChildren(int v) {
    ArrayList<Integer> children = new ArrayList<Integer>();
    for (int i = 0; i < parent.length; i++) {
      if (parent[i] == v) {
        children.add(i);
      }
    }
    return children;
  }
  // TODO: Add print method
}
