package dijkstra;

import java.util.List;
import java.util.Map;

public record Graph(Map<Integer, List<Edge>> graph) {
  public List<Edge> edges(int node) {
    return graph.get(node);
  }

  public Map<Integer, List<Edge>> graph() {
    return graph;
  }
}
