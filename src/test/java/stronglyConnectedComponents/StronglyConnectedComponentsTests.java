package stronglyConnectedComponents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class StronglyConnectedComponentsTests {

  @Test
  void should_loadGraphAsAdjacencyLists_one() {
    final StronglyConnectedComponents scc = new StronglyConnectedComponents();
    final Graphs graphs = scc.loadGraph("stronglyConnectedComponents/test-graph-1.txt");
    assertEquals(graphs.graph().size(), 1, "Graph should be size 1");
    assertEquals(graphs.reverseGraph().size(), 3, "Reverse graph should be size 3");
  }

  @Test
  void should_loadGraphAsAdjacencyLists_three() {
    final StronglyConnectedComponents scc = new StronglyConnectedComponents();
    final Graphs graphs = scc.loadGraph("stronglyConnectedComponents/test-graph-3.txt");
    assertEquals(graphs.graph().size(), 9, "Graph should be size 9");
    assertEquals(graphs.reverseGraph().size(), 9, "Reverse graph should be size 9");
  }

  @ParameterizedTest
  @ValueSource(strings = {"stronglyConnectedComponents/test-graph-2.txt", "stronglyConnectedComponents/test-graph-3.txt"})
  void should_DFSAllNodes() {
    final StronglyConnectedComponents scc = new StronglyConnectedComponents();
    final Graphs graphs = scc.loadGraph("stronglyConnectedComponents/test-graph-2.txt");

    // Assert no nodes have been visited
    graphs.graph().forEach((sourceNode, tailNodes) -> {
      assertFalse(sourceNode.visited());
      tailNodes.forEach(tailNode -> assertFalse(tailNode.visited()));
    });

    scc.DFS(graphs, graphs.nodes().get(1));

    // Assert all nodes have been visited
    graphs.graph().forEach((sourceNode, tailNodes) -> {
      assertTrue(sourceNode.visited(), "Node " + sourceNode.node() + " was not visited.");
      tailNodes.forEach(tailNode -> assertTrue(tailNode.visited(), "Node " + tailNode.node() + " was not visited."));
    });
  }

  @ParameterizedTest
  @ValueSource(strings = {"stronglyConnectedComponents/test-graph-3.txt", "stronglyConnectedComponents/test-graph-4.txt"})
  void should_applyKosarajuAndObtainSCCs_three(String graphFile) {
    final StronglyConnectedComponents scc = new StronglyConnectedComponents();
    final Graphs graphs = scc.loadGraph(graphFile);

    scc.Kosaraju(graphs, 9);
  }

}
