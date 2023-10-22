package DataStructures.AVL;

public class Node {
  private int val;
  private Node left;
  private Node right;
  private Node parent;

  Node(int val, Node parent) {
    this.val = val;
    this.parent = parent;
  }

  public int getVal() {
    return val;
  }

  public void setVal(int val) {
    this.val = val;
  }

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public void rotateRightNode() {
    // this is x
    Node r = this.getRight();
    Node a = r.getLeft();
    Boolean child = checkChild();

    r.setParent(this.getParent());
    this.setParent(r);
    if (child != null) {
      if (child) {
        r.getParent().setLeft(r);
      } else {
        r.getParent().setRight(r);
      }
    }
    r.setLeft(this);
    this.setRight(a);
    if (a != null) {
      a.setParent(this);
    }
  }

  public void rotateLeftNode() {
    // this is r
    Node a = this.getLeft();
    Node d = a.getRight();
    Boolean child = checkChild();

    a.setParent(this.getParent());
    this.setParent(a);
    if (child != null) {
      if (child) {
        a.getParent().setLeft(a);
      } else {
        a.getParent().setRight(a);
      }
    }

    a.setRight(this);
    this.setLeft(d);
    if (d != null) {
      d.setParent(this);
    }
  }

  private Boolean checkChild() {
    // true = left, false = right, null = root
    // check if this is the left child or the right child
    if (this.getParent() != null && this.getParent().getLeft() == this) {
      return true;
    } else if (this.getParent() != null && this.getParent().getRight() == this) {
      return false;
    } else {
      return null;
    }
  }
}
