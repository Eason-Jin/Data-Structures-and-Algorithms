package DataStructures.BinarySearchTree;

public class TreeSet {
  Node root;

  public boolean contains(int val) {
    Node node = root;
    // Start from the root (head) and go down
    while (node != null) {
      // Found
      if (node.getVal() == val) return true;
      // Value is greater than current node, go right
      if (val > node.getVal()) {
        node = node.getRight();
        continue;
      }
      // Value is less than current node, go left
      if (val < node.getVal()) {
        node = node.getLeft();
        continue;
      }
    }
    return false;
  }
  public void add(int val) {
    // Set the first element
    if (root == null) {
      root = new Node(val, null);
      return;
    }

    Node parent = null;
    Node node = root;
    while (node != null) {
      // Already in set
      if (node.getVal() == val) return;

      // Find the parent of the new node
      parent = node;
      if (val > node.getVal()) node = node.getRight();
      else node = node.getLeft();
    }

    // Decide which side the new node is to parent
    Node newNode = new Node(val, parent);
    if (val > parent.getVal()) parent.setRight(newNode);
    else parent.setLeft(newNode);

    // Balance the tree
    while (parent != null) {
      root = updateRoot();
      if (!isBalanced(parent)) {
        balance(parent);
      }
      parent = parent.getParent();
    }
  }

  public Node findMax(Node node) {
    // Keep going right
    while (node.getRight() != null) {
      node = node.getRight();
    }
    return node;
  }

  public Node findMin(Node node) {
    // Keep going left
    while (node.getLeft() != null) {
      node = node.getLeft();
    }
    return node;
  }

  // Find the next largest node
  public Node findNextLarger(Node node) {
    // If right node is not null, find the minimum of the right branch
    if (node.getRight() != null) return findMin(node.getRight());
    else {
      // Right node is null
      // As long as the node is not the left child (if it is then its parent is larger)
      // Move up the tree
      while (node.getParent() != null && node.getParent().getLeft() != node) {
        node = node.getParent();
      }
      return node.getParent();
    }
  }

  // Consider node with one or no children
  public void simpleRemove(Node node) {
    // Node has one left child branch
    if (node.getLeft() != null) {
      if (node.getParent() == null) {
        // Node is the root
        root = node.getLeft(); // Move the root to the left element
      } else if (node.getParent().getLeft() == node) {
        // Node is the left child
        node.getParent().setLeft(node.getLeft()); // Join the branch
      } else {
        // Node is the right child
        node.getParent().setRight(node.getLeft()); // Join the branch
      }
    } else if (node.getRight() != null) {
      // Node has one right child branch
      if (node.getParent() == null) {
        // Node is the root
        root = node.getRight(); // Move the root to the right element
      } else if (node.getParent().getRight() == node) {
        // Node is the left child
        node.getParent().setLeft(node.getRight()); // Join the branch
      } else {
        // Node is the right child
        node.getParent().setRight(node.getRight()); // Join the branch
      }
    } else {
      // Node has no children
      if (node == root) {
        // Node is root
        root = null;
      } else {
        if (node.getParent().getLeft() == node) {
          node.getParent().setLeft(null);
        } else {
          node.getParent().setRight(null);
        }
      }
    }
  }

  public void remove(int val) {
    Node node = find(val);
    Node parent;
    // Value not in the set
    if (node == null) return;
    if (node.getRight() != null && node.getLeft() != null) {
      // Two children
      // Find the next largest node, copy its value and replace the deleted node with the minimum
      // node
      Node nextNode = findNextLarger(node);
      // Joins the branch of next minimum to the parent of it (it has replaced the deleted node)
      parent = nextNode.getParent();
      simpleRemove(nextNode);
      node.setVal(nextNode.getVal());
      // Balance the tree
      while (parent != null) {
        // root = updateRoot();
        if (!isBalanced(parent)) {
          balance(parent);
        }
        parent = parent.getParent();
      }
    } else {
      // Only one child or no children
      parent = node.getParent();
      simpleRemove(node);
      // Balance the tree
      while (parent != null) {
        // root = updateRoot();
        if (!isBalanced(parent)) {
          balance(parent);
        }
        parent = parent.getParent();
      }
    }
  }

  public Node find(int val) {
    Node node = root;
    while (node != null) {
      if (node.getVal() == val) {
        return node;
      } else if (val < node.getVal()) {
        node = node.getLeft();
      } else {
        node = node.getRight();
      }
    }
    return null;
  }

  public void balance(Node x) {
    if (getHeightRecursive(x.getLeft()) > getHeightRecursive(x.getRight())) {
      // Case 1
      Node l = x.getLeft();
      if (getHeightRecursive(l.getLeft()) >= getHeightRecursive(l.getRight())) {
        // Case 1.1
        x.rotateLeftNode();
        if (x == root) {
          root = l;
        }
      } else {
        // Case 1.2
        Node r = null;
        if (l.getRight() != null) {
          r = l.getRight();
        }
        l.rotateRightNode();
        x.rotateLeftNode();
        if (x == root && r != null) {
          root = r;
        }
      }
    } else {
      // Case 2
      Node r = x.getRight();
      if (getHeightRecursive(r.getLeft()) <= getHeightRecursive(r.getRight())) {
        // Case 2.1
        x.rotateRightNode();
        if (x == root) {
          root = r;
        }
      } else {
        // Case 2.2
        Node l = null;
        if (r.getLeft() != null) {
          l = r.getLeft();
        }
        r.rotateLeftNode();
        x.rotateRightNode();
        if (x == root && l != null) {
          root = l;
        }
      }
    }
    // root = updateRoot();
  }

  private Boolean isBalanced(Node node) {
    Node leftNode = node.getLeft();
    Node rightNode = node.getRight();
    int leftHeight = getHeightRecursive(leftNode);
    int rightHeight = getHeightRecursive(rightNode);
    if (leftNode == null) leftHeight = -1;
    if (rightNode == null) rightHeight = -1;
    return Math.abs(leftHeight - rightHeight) <= 1;
  }

  public int getHeight(int val) {
    return getHeightRecursive(find(val));
  }

  private int getHeightRecursive(Node node) {
    if (node == null || (node.getLeft() == null && node.getRight() == null)) return 0;
    int leftHeight = 0;
    int rightHeight = 0;
    if (node.getLeft() != null) leftHeight = getHeightRecursive(node.getLeft());
    if (node.getRight() != null) rightHeight = getHeightRecursive(node.getRight());
    return Math.max(leftHeight, rightHeight) + 1;
  }

  public void print() {
    displayTree(root, "", false);
  }

  // Call with displayTree(root, "", false);
  private void displayTree(Node root, String prefix, boolean isLeft) {
    if (root != null) {
      System.out.println(prefix + (isLeft ? "├── L: " : "└── R: ") + root.getVal());
      displayTree(root.getLeft(), prefix + (isLeft ? "│   " : "    "), true);
      displayTree(root.getRight(), prefix + (isLeft ? "│   " : "    "), false);
    }
  }

  private Node updateRoot() {
    while (root.getParent() != null) {
      root = root.getParent();
    }
    return root;
  }
}
