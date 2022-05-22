package dijkstra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ShortestPath {

  public static void main(String[] args) {

  }

  Graph loadGraph(String filename) {
    final Map<Integer, List<Edge>> graph = new HashMap<>();

    try (Stream<String> stream = Files.lines(Path.of("C:\\Users\\young\\dev\\algorithms\\src\\main\\resources\\dijkstra\\" + filename))) {
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
        System.out.println(nodeId + ": " + edges);
        graph.put(nodeId, edges);
      });
    } catch (IOException e) {
      System.err.println("Problem reading " + filename);
      e.printStackTrace();
    }
    return new Graph(graph);
  }
}
