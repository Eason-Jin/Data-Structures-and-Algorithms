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
    if (parent[x] != x) {
      parent[x] = findParent(parent[x]); // Path compression
    }
    return parent[x];
  }

  public boolean union(int x, int y) {
    int rootX = findParent(x);
    int rootY = findParent(y);

    if (rootX == rootY) {
      return false; // x and y are already in the same set
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

    return true;
  }
}