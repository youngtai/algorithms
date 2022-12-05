package heaps;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedianMaintenanceTests {

  private final MedianMaintenance fixture = new MedianMaintenance();

  @Test
  void getMedianK_SimpleTests_Ascending() {
    PriorityQueue<Integer> high = new PriorityQueue<>();  //minHeap
    PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());  //maxHeap

    final int result1 = fixture.getMedianK(low, high, 1);
    assertEquals(result1, 1);

    final int result2 = fixture.getMedianK(low, high, 2);
    assertEquals(result2, 1);

    final int result3 = fixture.getMedianK(low, high, 3);
    assertEquals(result3, 2);

    final int result4 = fixture.getMedianK(low, high, 4);
    assertEquals(result4, 2);

    final int result5 = fixture.getMedianK(low, high, 5);
    assertEquals(result5, 3);
  }

  @Test
  void getMedianK_SimpleTests_Descending() {
    PriorityQueue<Integer> high = new PriorityQueue<>();  //minHeap
    PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());  //maxHeap

    final int result5 = fixture.getMedianK(low, high, 5);
    assertEquals(result5, 5);

    final int result4 = fixture.getMedianK(low, high, 4);
    assertEquals(result4, 4);

    final int result3 = fixture.getMedianK(low, high, 3);
    assertEquals(result3, 4);

    final int result2 = fixture.getMedianK(low, high, 2);
    assertEquals(result2, 3);

    final int result1 = fixture.getMedianK(low, high, 1);
    assertEquals(result1, 3);
  }

  @Test
  void runMedianMaintenance_SimpleTests_Ascending() {
    final int sum1 = fixture.runMedianMaintenance(Stream.of("1"));
    assertEquals(sum1, 1);

    final int sum2 = fixture.runMedianMaintenance(Stream.of("1", "2"));
    assertEquals(sum2, 2);

    final int sum3 = fixture.runMedianMaintenance(Stream.of("1", "2", "3"));
    assertEquals(sum3, 4);

    final int sum4 = fixture.runMedianMaintenance(Stream.of("1", "2", "3", "4"));
    assertEquals(sum4, 6);

    final int sum5 = fixture.runMedianMaintenance(Stream.of("1", "2", "3", "4", "5"));
    assertEquals(sum5, 9);
  }

  @Test
  void runMedianMaintenance_SimpleTests_Descending() {
    final int sum1 = fixture.runMedianMaintenance(Stream.of("5", "4", "3", "2", "1"));
    assertEquals(sum1, 19);

    final int sum2 = fixture.runMedianMaintenance(Stream.of("5", "4", "3", "2"));
    assertEquals(sum2, 16);

    final int sum3 = fixture.runMedianMaintenance(Stream.of("5", "4", "3"));
    assertEquals(sum3, 13);

    final int sum4 = fixture.runMedianMaintenance(Stream.of("5", "4"));
    assertEquals(sum4, 9);

    final int sum5 = fixture.runMedianMaintenance(Stream.of("5"));
    assertEquals(sum5, 5);
  }

  @Test
  void rebalanceHeaps_ShouldDoNothing() {
    PriorityQueue<Integer> high = new PriorityQueue<>();  //minHeap
    PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());  //maxHeap
    low.addAll(List.of(1, 2, 3));
    high.addAll(List.of(4, 5));

    fixture.rebalanceHeaps(low, high);
    assertEquals(low.peek(), 3);
    assertEquals(low.size(), 3);
    assertEquals(high.peek(), 4);
    assertEquals(high.size(), 2);
  }

  @Test
  void rebalanceHeaps_ShouldRebalance() {
    PriorityQueue<Integer> high = new PriorityQueue<>();  //minHeap
    PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());  //maxHeap
    low.add(1);
    high.addAll(List.of(2, 3, 4));

    fixture.rebalanceHeaps(low, high);
    assertEquals(low.peek(), 2);
    assertEquals(low.size(), 2);
    assertEquals(high.peek(), 3);
    assertEquals(high.size(), 2);
  }
}
