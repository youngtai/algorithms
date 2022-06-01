package dijkstra;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortestPathTests {
  private final ShortestPath sp = new ShortestPath();
  final Graph graph1 = sp.loadGraph("test-1.txt");
  final Graph graph2 = sp.loadGraph("test-2.txt");

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
    assertEquals(sp.dijkstra(graph1, 1), 0);
    assertEquals(sp.dijkstra(graph1, 2), 1);
    assertEquals(sp.dijkstra(graph1, 3), 3);
    assertEquals(sp.dijkstra(graph1, 4), 5);
  }

  @Test
  void shouldRunDijkstra_test2() {
    System.out.println("Target: 1");
    assertEquals(sp.dijkstra(graph2, 1), 0);
    System.out.println("Target: 2");
    assertEquals(sp.dijkstra(graph2, 2), 1);
    System.out.println("Target: 3");
    assertEquals(sp.dijkstra(graph2, 3), 2);
    System.out.println("Target: 4");
    assertEquals(sp.dijkstra(graph2, 4), 3);
    System.out.println("Target: 5");
    assertEquals(sp.dijkstra(graph2, 5), 4);
    System.out.println("Target: 6");
    assertEquals(sp.dijkstra(graph2, 6), 4);
    System.out.println("Target: 7");
    assertEquals(sp.dijkstra(graph2, 7), 3);
    System.out.println("Target: 8");
    assertEquals(sp.dijkstra(graph2, 8), 2);
  }
}
