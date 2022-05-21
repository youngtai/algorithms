package stronglyConnectedComponents;

import java.util.List;
import java.util.Map;

public record Graphs(Map<Node, List<Node>> graph,
                     Map<Node, List<Node>> reverseGraph,
                     Map<Integer, Node> nodes) {

  public Map<Node, List<Node>> graph() {
    return graph;
  }

  public Map<Node, List<Node>> reverseGraph() {
    return reverseGraph;
  }

  public Map<Integer, Node> nodes() {
    return nodes;
  }
}
