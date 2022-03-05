package randomContraction;

import minimumCut.KargerRandomContraction;
import minimumCut.KargerRandomContraction.Edge;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KargerRandomContractionTests {

  @Test
  void shouldGetEdge() {
    final List<List<Integer>> graph = Arrays.asList(Arrays.asList(0, 1, 2, 3), Arrays.asList(1, 2, 3));
    final KargerRandomContraction krc = new KargerRandomContraction();
    final Edge edge1 = krc.getEdge(graph, 0);
    assertEquals(edge1.getU(), 0, "Edge1: u should be 0");
    assertEquals(edge1.getV(), 1, "Edge1: v should be 1");
    final Edge edge2 = krc.getEdge(graph, 3);
    assertEquals(edge2.getU(), 1, "Edge2: u should be 1");
    assertEquals(edge2.getV(), 2, "Edge2: v should be 2");
  }

  @Test
  void shouldGetRandomEdgeIndex() {
    final List<List<Integer>> graph = Arrays.asList(
        Arrays.asList(1,2,3,4,7),
        Arrays.asList(2,1,3,4),
        Arrays.asList(3,1,2,4),
        Arrays.asList(4,1,2,3,5),
        Arrays.asList(5,4,6,7,8),
        Arrays.asList(6,5,7,8),
        Arrays.asList(7,1,5,6,8),
        Arrays.asList(8,5,6,7));
    final KargerRandomContraction krc = new KargerRandomContraction();
    // 0 <= output <= 27 uniformly
    for (int iteration = 0; iteration < 10; iteration++) {
      System.out.println(krc.getRandomEdgeIndex(graph));
    }
  }

  @Test
  void shouldMergeVertices1() {
    final List<List<Integer>> graph = new LinkedList<>();
    final List<Integer> adjacencyList0 = new LinkedList<>();
    final List<Integer> adjacencyList1 = new LinkedList<>();
    final List<Integer> adjacencyList2 = new LinkedList<>();
    final List<Integer> adjacencyList3 = new LinkedList<>();
    adjacencyList0.add(1);
    adjacencyList0.add(3);
    adjacencyList1.add(2);
    adjacencyList1.add(3);
    adjacencyList1.add(4);
    adjacencyList2.add(3);
    adjacencyList2.add(1);
    adjacencyList2.add(2);
    adjacencyList3.add(4);
    adjacencyList3.add(2);
    graph.add(adjacencyList0);
    graph.add(adjacencyList1);
    graph.add(adjacencyList2);
    graph.add(adjacencyList3);
    final KargerRandomContraction krc = new KargerRandomContraction();
    final Edge edge = new Edge(1, 3);
    krc.mergeVertices(edge, graph);
    // Expecting [[1, 2], [2, 1, 4], [4, 2]]
    assertEquals(graph, Arrays.asList(Arrays.asList(1,2), Arrays.asList(2,1,4), Arrays.asList(4,2)));
  }

  @Test
  void shouldMergeVertices2() {
    final List<List<Integer>> graph = new LinkedList<>();
    final List<Integer> adjacencyList0 = new LinkedList<>();
    final List<Integer> adjacencyList1 = new LinkedList<>();
    final List<Integer> adjacencyList2 = new LinkedList<>();
    final List<Integer> adjacencyList3 = new LinkedList<>();
    adjacencyList0.add(1);
    adjacencyList0.add(2);
    adjacencyList0.add(3);
    adjacencyList1.add(2);
    adjacencyList1.add(1);
    adjacencyList1.add(3);
    adjacencyList1.add(4);
    adjacencyList2.add(3);
    adjacencyList2.add(1);
    adjacencyList2.add(2);
    adjacencyList3.add(4);
    adjacencyList3.add(2);
    graph.add(adjacencyList0);
    graph.add(adjacencyList1);
    graph.add(adjacencyList2);
    graph.add(adjacencyList3);
    final KargerRandomContraction krc = new KargerRandomContraction();
    final Edge edge = new Edge(1, 3);
    krc.mergeVertices(edge, graph);
    // Expecting [[1, 2, 2], [2, 1, 1, 4], [4, 2]]
    assertEquals(graph, Arrays.asList(Arrays.asList(1,2,2), Arrays.asList(2,1,1,4), Arrays.asList(4,2)));
  }
}
