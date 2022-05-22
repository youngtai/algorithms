package dijkstra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathTests {

  @Test
  void shouldLoadGraph() {
    final ShortestPath sp = new ShortestPath();
    final Graph graph = sp.loadGraph("test-1.txt");
    assertEquals(4, graph.graph().size());
  }
}
