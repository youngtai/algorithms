package minimumCut;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class KargerRandomContraction {

  public record Edge(int vertexU, int vertexV) {
    public int getU() {
      return vertexU;
    }

    public int getV() {
      return vertexV;
    }
  }

  private static final Random random = new Random();

  public static void main(String[] args) {
    List<Integer> cutCounts = new ArrayList<>();
    KargerRandomContraction krc = new KargerRandomContraction();
    for (int iterationIndex = 0; iterationIndex < 100; iterationIndex++) {
      final List<List<Integer>> graph = loadGraph("kargerMinCut-adjacencyList.txt");
      krc.getMinCutCandidate(graph);
      final int cutCount = (graph.get(0).size() + graph.get(0).size() - 2) / 2;
      cutCounts.add(cutCount);
    }
    System.out.println("Min: " + Collections.min(cutCounts));
    System.out.println(cutCounts);

    // Big graph should be 14, test1 2, test2 1.
  }

  /**
   * while there are more than 2 vertices
   *   pick a remaining edge (u,v) uniformly at random
   *   merge ("contract") u and v into a single vertex
   *   remove self-loops
   * return cut represented by final 2 vertices
   */
  public void getMinCutCandidate(List<List<Integer>> graph) {
    while (graph.size() > 2) {
      final Edge randomEdge = getRandomEdge(graph);
      mergeVertices(randomEdge, graph);
    }
//    System.out.println(graph);
  }

  private Edge getRandomEdge(List<List<Integer>> graph) {
    final int randomEdgeIndex = getRandomEdgeIndex(graph);
    return getEdge(graph, randomEdgeIndex);
  }

  public Edge getEdge(List<List<Integer>> graph, int edgeIndex) {
    int maxIndex = 0;
    for (List<Integer> adjacencyList : graph) {
      if (edgeIndex < maxIndex + adjacencyList.size() - 1) {
        return new Edge(adjacencyList.get(0), adjacencyList.get(edgeIndex - maxIndex + 1));
      } else {
        maxIndex += adjacencyList.size() - 1;
      }
    }
    return null;
  }

  public Integer getRandomEdgeIndex(List<List<Integer>> graph) {
    int edgeCount = 0;
    for (final List<Integer> adjacencyList : graph) {
      edgeCount += adjacencyList.size() - 1;
    }
    return random.nextInt(0, edgeCount);
  }

  public void mergeVertices(Edge edge, List<List<Integer>> graph) {
    // Given pair u, v
    // iterate backwards through graph
    // if we encounter v adj list, copy over adjacent nodes to u, remove v adj list
    // if we encounter u adj list, copy over adjacent nodes from v, remove self loops
    // otherwise, change v to u
    final int u = edge.getU();
    final int v = edge.getV();

    // We could use linear-time selection to find and remove the list beginning with v
    int uAdjacencyListIndex = -1;
    List<Integer> verticesToAddFromVToU = new ArrayList<>();
    for (int graphIndex = graph.size() - 1; graphIndex >= 0; graphIndex--) {
      if (graph.get(graphIndex).get(0) == u) {
        encounteredU(graph, u, v, verticesToAddFromVToU, graphIndex);
        uAdjacencyListIndex = graphIndex;
      } else if (graph.get(graphIndex).get(0) == v) {
        encounteredV(graph, u, uAdjacencyListIndex, verticesToAddFromVToU, graphIndex);
      } else {
        for (int vertexIndex = graph.get(graphIndex).size() - 1; vertexIndex > 0; vertexIndex--) {
          if (graph.get(graphIndex).get(vertexIndex) == v) {
            graph.get(graphIndex).set(vertexIndex, u);
          }
        }
      }
    }
  }

  private void encounteredV(List<List<Integer>> graph, int u, int uAdjacencyListIndex, List<Integer> verticesToAddFromVToU, int graphIndex) {
    for (int vertexIndex = 1; vertexIndex < graph.get(graphIndex).size(); vertexIndex++) {
      verticesToAddFromVToU.add(graph.get(graphIndex).get(vertexIndex));
    }
    if (uAdjacencyListIndex != -1) {
      for (int vertex : verticesToAddFromVToU) {
        if (vertex != u) {
          graph.get(uAdjacencyListIndex).add(vertex);
        }
      }
    }
    graph.remove(graphIndex);
  }

  private void encounteredU(List<List<Integer>> graph, int u, int v, List<Integer> verticesToAddFromVToU, int graphIndex) {
    if (verticesToAddFromVToU.size() > 0) {
      for (int vertex : verticesToAddFromVToU) {
        if (vertex != u) {
          graph.get(graphIndex).add(vertex);
        }
      }
    }
    for (int vertexIndex = graph.get(graphIndex).size() - 1; vertexIndex > 0; vertexIndex--) {
      if (graph.get(graphIndex).get(vertexIndex) == v) {
        graph.get(graphIndex).remove(vertexIndex);
      }
    }
  }

  private static List<List<Integer>> loadGraph(String filename) {
    final List<List<Integer>> lists = new LinkedList<>();
    try (Stream<String> stream = Files.lines(Paths.get(Resources.getResource(filename).getPath()))) {
      stream.forEach(line -> {
        final String[] splitLine = line.split("\t");
        final List<Integer> adjacencyList = new LinkedList<>();
        for (String entry : splitLine) {
          adjacencyList.add(Integer.valueOf(entry));
        }
        lists.add(adjacencyList);
      });
    } catch (IOException e) {
      System.err.println("Problem reading " + filename);
    }
    return lists;
  }
}
