package dijkstra;

public record Edge(int startId, int endId, int length) {
  public int startId() {
    return startId;
  }

  public int endId() {
    return endId;
  }

  public int length() {
    return length;
  }
}
