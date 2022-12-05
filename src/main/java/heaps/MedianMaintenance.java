package heaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * Implement the "Median Maintenance" algorithm. The text file contains a list of integers from 1 to 10000 in unsorted
 * order. Letting x_i denote the i^{th} number of the file, the k^{th} median m_k is defined as the median of the
 * numbers x_1, ..., x_k. (So, if k is odd, then m_k is ((k+1/2)th smallest number among x_1, ..., x_k; if k is even,
 * then m_k is the (k/2)th smallest number among x_1, ..., x_k)
 */
public class MedianMaintenance {

  public static void main(String[] args) {
    final MedianMaintenance mm = new MedianMaintenance();

    final int sum = mm.runMedianMaintenance(mm.stringStream());
    System.out.println(sum);  // Final sum should be 46831213
    System.out.println(sum % 10000);
  }

  public int runMedianMaintenance(Stream<String> stream) {
    PriorityQueue<Integer> high = new PriorityQueue<>();  //minHeap
    PriorityQueue<Integer> low = new PriorityQueue<>(Comparator.reverseOrder());  //maxHeap
    AtomicInteger sum = new AtomicInteger();
    stream.forEach(line -> {
      final int x_k = Integer.parseInt(line);
      // Add x_k to the correct heap
      int median_k = getMedianK(low, high, x_k);
      sum.addAndGet(median_k);
    });
    return sum.get();
  }

  public int getMedianK(PriorityQueue<Integer> low, PriorityQueue<Integer> high, int x_k) {
    // Add x_k to the correct heap
    addToCorrectHeap(low, high, x_k);

    // Check whether heaps are balanced
    rebalanceHeaps(low, high);

    // If the two heaps have equal size, the median is in low
    // If the two heaps are different sizes, the median is in the larger heap (and is min/max accordingly)
    int median_k;
    if (low.size() == high.size()) {
      median_k = low.peek();
    } else if (low.size() < high.size()) {
      median_k = high.peek();
    } else {
      median_k = low.peek();
    }
    return median_k;
  }

  private void addToCorrectHeap(PriorityQueue<Integer> low, PriorityQueue<Integer> high, int x_k) {
    if (low.isEmpty() || x_k <= low.peek()) {
      low.add(x_k);
    } else {
      high.add(x_k);
    }
  }

  public void rebalanceHeaps(PriorityQueue<Integer> low, PriorityQueue<Integer> high) {
    if (low.size() - high.size() == 2) {
      high.add(low.remove());
    } else if (high.size() - low.size() == 2) {
      low.add(high.remove());
    }
  }

  private Stream<String> stringStream() {
    try {
      return Files.lines(Path.of("/Users/youngtai/personal/algorithms/src/main/resources/median-maintenance-data.txt"));
    } catch (IOException e) {
      System.err.println("Problem reading " + "median-maintenance-data.txt");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
