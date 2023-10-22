package DataStructures.BinarySearchTree;

public class Main {
  public static void main(String[] args) {
    TreeSet treeSet = new TreeSet();

    int n = 100;
    for (int i = n - 1; i >= 0; i--) {
      treeSet.add(i);
    }

    // for (int j = 0; j < n / 2; j++) {
    //   treeSet.remove(j);
    // }

    treeSet.print();
  }
}
