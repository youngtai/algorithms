package stronglyConnectedComponents;

public class Node {
  private final int node;

  private boolean visited;

  Node(int node, boolean visited) {
    this.node = node;
    this.visited = visited;
  }

  void setVisited(boolean visited) {
    this.visited = visited;
  }

  boolean visited() {
    return visited;
  }

  int node() {
    return node;
  }
}
