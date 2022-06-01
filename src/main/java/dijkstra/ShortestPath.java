package dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.out;

public class ShortestPath {

  public static void main(String[] args) {
    final ShortestPath sp = new ShortestPath();
    final Graph graph = sp.loadGraph("dijkstra-graph.txt");
    //Targets: 7,37,59,82,99,115,133,165,188,197
    //Outputs: 2599,2610,2947,2052,2367,2399,2029,2442,2505,3068
    out.println(sp.dijkstra(graph, 7));
    out.println(sp.dijkstra(graph, 37));
    out.println(sp.dijkstra(graph, 59));
    out.println(sp.dijkstra(graph, 82));
    out.println(sp.dijkstra(graph, 99));
    out.println(sp.dijkstra(graph, 115));
    out.println(sp.dijkstra(graph, 133));
    out.println(sp.dijkstra(graph, 165));
    out.println(sp.dijkstra(graph, 188));
    out.println(sp.dijkstra(graph, 197));
  }

  /**
   * while not all nodes are visited
   *   among all candidate edges, choose the one that minimizes the length from the candidate edge tail + the edge length
   *   add the length minimizing edge head to the set of visited notes
   *   set the shortest path of the length minimizing edge head as: the shortest path to its tail + its length
   *
   * @param graph The undirected graph stored as an adjacency list
   * @param targetNode The node we are trying to find a shortest path to
   * @return The length of the shortest path to the target node
   */
  int dijkstra(Graph graph, int targetNode) {
    final Set<Integer> visitedNodes = new HashSet<>();
    final int startNode = 1;
    visitedNodes.add(startNode);  // start node is 1
//    out.println("visited: " + visitedNodes);
    final Map<Integer, Integer> shortestPaths = new HashMap<>();
    graph.graph().keySet().forEach(node -> shortestPaths.put(node, 0));
//    out.println("shortest: " + shortestPaths);
    while (!visitedNodes.equals(graph.graph().keySet()) && !visitedNodes.contains(targetNode)) {
      final List<Edge> candidateEdges = visitedNodes.stream()
          .flatMap(node -> graph.edges(node).stream())
          .filter(edge -> !visitedNodes.contains(edge.endId()))
          .toList();
      final Edge lengthMinimizingEdge = candidateEdges.stream()
          .min(Comparator.comparing(edge -> shortestPaths.get(edge.startId()) + edge.length()))
          .orElseThrow(NoSuchElementException::new);
      shortestPaths.put(lengthMinimizingEdge.endId(), shortestPaths.get(lengthMinimizingEdge.startId()) + lengthMinimizingEdge.length());
      visitedNodes.add(lengthMinimizingEdge.endId());
//      out.println("candidates: " + candidateEdges);
//      out.println("visited: " + visitedNodes);
//      out.println("shortest: " + shortestPaths + "\n");
    }
    return shortestPaths.get(targetNode);
  }

  Graph loadGraph(String filename) {
    final Map<Integer, List<Edge>> graph = new HashMap<>();

    try (Stream<String> stream = Files.lines(Path.of("/home/youngtai/IdeaProjects/algorithms/src/main/resources/dijkstra/" + filename))) {
      stream.forEach(line -> {
        final List<String> splitLine = Arrays.stream(line.split("\t")).toList();
        final int nodeId = Integer.parseInt(splitLine.get(0));
        final List<Edge> edges = splitLine
            .subList(1, splitLine.size()).stream()
            .map(edgeData -> {
              final Integer[] edgeDataSplit = Arrays.stream(edgeData.split(","))
                  .map((Integer::parseInt))
                  .toArray(Integer[]::new);
              return new Edge(nodeId, edgeDataSplit[0], edgeDataSplit[1]);
            }).toList();
//        System.out.println(nodeId + ": " + edges);
        graph.put(nodeId, edges);
      });
    } catch (IOException e) {
      System.err.println("Problem reading " + filename);
      e.printStackTrace();
    }
    return new Graph(graph);
  }
}
