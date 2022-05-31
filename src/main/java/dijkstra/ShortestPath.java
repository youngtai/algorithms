package dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShortestPath {

  public static void main(String[] args) {

  }

  int dijkstra(Graph graph, int targetNode) {
    final Set<Integer> visitedNodes = new HashSet<>();
    final int startNode = 1;
    visitedNodes.add(startNode);  // start node is 1

    System.out.println(visitedNodes);

//    int shortestPath = 0;
    final Map<Integer, Integer> shortestPaths = new HashMap<>();
    graph.graph().keySet().forEach(node -> shortestPaths.put(node, 0));

    System.out.println(shortestPaths);

    while (!visitedNodes.equals(graph.graph().keySet()) && !visitedNodes.contains(targetNode)) {
      final List<Edge> candidateEdges = visitedNodes.stream()
          .flatMap(node -> graph.edges(node).stream())
          .filter(edge -> !visitedNodes.contains(edge.endId()))
          .toList();
      // compute shortest path from start node to each edge head
//      candidateEdges.forEach(candidateEdge -> {
//        final int headNode = candidateEdge.endId();
//        shortestPaths.put(headNode, shortestPaths.get(headNode) + candidateEdge.length());
//      });

      final Edge lengthMinimizingEdge = candidateEdges.stream()
          .min(Comparator.comparing(edge -> shortestPaths.get(edge.endId())))
          .orElseThrow(NoSuchElementException::new);
      shortestPaths.put(lengthMinimizingEdge.endId(), shortestPaths.get(lengthMinimizingEdge.endId()) + lengthMinimizingEdge.length());
      // Update
      visitedNodes.add(lengthMinimizingEdge.endId());
      System.out.println(visitedNodes);
      System.out.println(shortestPaths);
//      shortestPath += lengthMinimizingEdge.length();
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
