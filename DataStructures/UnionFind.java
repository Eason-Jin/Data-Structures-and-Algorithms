package DataStructures;

class UnionFind {
  int parent[];
  int height[];

  UnionFind(int n) {
    parent = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = -1;
      height[i] = 0;
    }
  }

  // Find the representative of an element
  int findRep(int x) {
    int rep = x;
    // Find the representative of the current branch
    while (parent[rep] != -1) {
      rep = parent[rep];
    }
    // Path compression, make a long tree fat instead
    while (parent[x] != rep) {
      int up = parent[x];
      parent[x] = rep; // Make each element in the branch directly relate to rep
      x = up; // Move x up 1
    }
    return rep;
  }

  boolean isSameBag(int a, int b) {
    // Check whether two elements have the same representative
    return findRep(a) == findRep(b);
  }

  void mergeBags(int a, int b) {
    a = findRep(a);
    b = findRep(b);
    // Already in the same bag
    if (a == b) return;

    // If a has shorter tree, make a child of b, is operation will not increase max height
    if (height[a] < height[b]) {
      parent[a] = b;
    } else {
      parent[b] = a;
      // When the two trees are the same height, join the two heads
      if (height[a] == height[b]) {
        height[a]++;
      }
    }
  }
}
