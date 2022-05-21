package stronglyConnectedComponents;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

// Kosaraju's Two-Pass Algorithm
public class StronglyConnectedComponents {

  /**
   * 875714 (there are 1 through 875714 nodes and must start from the "largest" node)
   * Due to the large size of the graph, heap size must be increased or the recursion will lead to a stack overflow.
   * Just add -Xss512m to the VM options before running.
   * Expect output: 434821, 968, 459, 313, 211
   */
  public static void main(String[] args) {
    StronglyConnectedComponents scc = new StronglyConnectedComponents();
    final Graphs graphs = scc.loadGraph("stronglyConnectedComponents/scc-graph.txt");

    scc.Kosaraju(graphs, 875714);
  }

  Graphs loadGraph(String filename) {
    final Map<Node, List<Node>> lists = new HashMap<>();
    final Map<Node, List<Node>> reverseLists = new HashMap<>();
    final Map<Integer, Node> nodes = new HashMap<>();

    try (Stream<String> stream = Files.lines(Path.of("C:\\Users\\young\\dev\\algorithms\\src\\main\\resources\\" + filename))) {
      stream.forEach(line -> {
        final Integer[] splitLine = Arrays.stream(line.split("\s")).map(Integer::valueOf).toArray(Integer[]::new);
        final Integer tailNode = splitLine[0];
        final Integer headNode = splitLine[1];

        nodes.putIfAbsent(tailNode, new Node(tailNode, false));
        nodes.putIfAbsent(headNode, new Node(headNode, false));

        if (lists.containsKey(nodes.get(tailNode))) {
          lists.get(nodes.get(tailNode)).add(nodes.get(headNode));
        } else {
          List<Node> adjacencyList = new ArrayList<>();
          adjacencyList.add(nodes.get(headNode));
          lists.put(nodes.get(tailNode), adjacencyList);
        }

        if (reverseLists.containsKey(nodes.get(headNode))) {
          reverseLists.get(nodes.get(headNode)).add(nodes.get(tailNode));
        } else {
          List<Node> adjacencyList = new ArrayList<>();
          adjacencyList.add(nodes.get(tailNode));
          reverseLists.put(nodes.get(headNode), adjacencyList);
        }
      });
    } catch (IOException e) {
      System.err.println("Problem reading " + filename);
      e.printStackTrace();
    }
    return new Graphs(lists, reverseLists, nodes);
  }

  /**
   * Pseudocode:
   * Mimic BFS code, use stack instead of a queue
   * OR
   * Recursive version
   * DFS(graph g, start vertex s)
   * - mark s as visited
   * - for every edge (s, v):
   *   - if v is unexplored
   *     - DFS(g, v)
   */
  void DFS(Graphs graphs, Node startNode) {
    startNode.setVisited(true);
    System.out.println("Visited node " + startNode.node());

    if (graphs.graph().containsKey(startNode)) {
      for (final Node headNode : graphs.graph().get(startNode)) {
        if (!headNode.visited()) {
          DFS(graphs, headNode);
        }
      }
    }
  }

  /**
   * For forward DFS pass on the reverse graph
   * @param graphs
   * @param finishingTimes
   * @param startNode
   */
  void DFS(Graphs graphs, Stack<Node> finishingTimes, Node startNode) {
    startNode.setVisited(true);

    // startNode is the tail, node is the head. The List<Node> define the edges with startNode as the tail.
    if (graphs.reverseGraph().containsKey(startNode)) {
      for (final Node headNode : graphs.reverseGraph().get(startNode)) {
        if (!headNode.visited()) {
          DFS(graphs, finishingTimes, headNode);
        }
      }
    }
    finishingTimes.add(startNode);
  }

  /**
   * For reverse DFS pass on the graph
   * @param graphs
   * @param leaderNodes
   * @param currentLeaderNode
   * @param startNode
   */
  void DFS(Graphs graphs, Map<Node, List<Node>> leaderNodes, Node currentLeaderNode, Node startNode) {
    startNode.setVisited(true);

    leaderNodes.get(currentLeaderNode).add(startNode);
    // startNode is the tail, node is the head. The List<Node> define the edges with startNode as the tail.
    if (graphs.graph().containsKey(startNode)) {
      for (final Node headNode : graphs.graph().get(startNode)) {
        if (!headNode.visited()) {
          DFS(graphs, leaderNodes, currentLeaderNode, headNode);
        }
      }
    }
  }

  void Kosaraju(Graphs graphs, int nodeCount) {
    // First DFS loop
    final Stack<Node> finishingTimes = new Stack<>();

    for (int node = nodeCount; node >= 1; node--) {
      if (!graphs.nodes().get(node).visited()) {
        DFS(graphs, finishingTimes, graphs.nodes().get(node));
      }
    }

    // Reset visit state of the graphs
    graphs.nodes().forEach((key, value) -> value.setVisited(false));

    // Second DFS loop
    final Map<Node, List<Node>> leaderNodes = new HashMap<>();

    while (!finishingTimes.isEmpty()) {
      final Node node = finishingTimes.pop();
      if (!node.visited()) {
        leaderNodes.put(node, new ArrayList<>());
        DFS(graphs, leaderNodes, node, node);
      }
    }

    leaderNodes.values().stream()
        .map(List::size)
        .sorted(Comparator.reverseOrder())
        .limit(5)
        .forEach(node -> System.out.print(node + " "));
  }
}
