package dijkstra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathTests {
  private final ShortestPath sp = new ShortestPath();
  final Graph graph1 = sp.loadGraph("test-1.txt");

  @Test
  void shouldLoadGraph() {
    assertEquals(4, graph1.graph().size());

    assertEquals(graph1.edges(1).get(0).startId(), 1);
    assertEquals(graph1.edges(1).get(0).endId(), 2);
    assertEquals(graph1.edges(1).get(0).length(), 1);

    assertEquals(graph1.edges(1).get(1).startId(), 1);
    assertEquals(graph1.edges(1).get(1).endId(), 3);
    assertEquals(graph1.edges(1).get(1).length(), 3);

    assertEquals(graph1.edges(2).get(0).startId(), 2);
    assertEquals(graph1.edges(2).get(0).endId(), 1);
    assertEquals(graph1.edges(2).get(0).length(), 1);

    assertEquals(graph1.edges(2).get(1).startId(), 2);
    assertEquals(graph1.edges(2).get(1).endId(), 4);
    assertEquals(graph1.edges(2).get(1).length(), 5);
  }

  @Test
  void shouldRunDijkstra_test1() {
    assertEquals(sp.dijkstra(graph1, 4), 5);
  }
}
