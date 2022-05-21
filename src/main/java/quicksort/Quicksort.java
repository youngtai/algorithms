package quicksort;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Quicksort {

  private final String PIVOT_METHOD;
  private final boolean DEBUG = false;
  private static final Random RANDOM = new Random();

  Quicksort() {
    this.PIVOT_METHOD = "middle";
  }

  Quicksort(String pivotMethod) {
    this.PIVOT_METHOD = pivotMethod;
  }

  public static void main(String[] args) {
    final List<Integer> integerList1 = loadIntegerList();
    final List<Integer> integerList2 = loadIntegerList();
    final List<Integer> integerList3 = loadIntegerList();
    final Quicksort quicksortLeft = new Quicksort("left");
    final Quicksort quicksortRight = new Quicksort("right");
    final Quicksort quicksortMiddle = new Quicksort("middle");

    System.out.println(quicksortLeft.sort(integerList1, 0, integerList1.size() - 1));
    // 162085
    System.out.println(quicksortRight.sort(integerList2, 0, integerList2.size() - 1));
    // 164123
    System.out.println(quicksortMiddle.sort(integerList3, 0, integerList3.size() - 1));
    // 138382
  }

  public int sort(List<Integer> integerList, int left, int right) {
    if (DEBUG) {
      System.out.println(integerList.subList(left, right + 1));
    }
    if (right - left < 1) {
      return 0;
    }

    final int pivotIndex = choosePivotIndex(integerList, left, right);
    // partition list around pivot
    final int pivotSplitIndex =  partition(integerList, left, right, pivotIndex);
    // sort left
    final int leftComparisons = sort(integerList, left, Math.max(pivotSplitIndex - 1, left));
    // sort right
    final int rightComparisons = sort(integerList, Math.min(pivotSplitIndex + 1, right), right);

    return (right - left) + leftComparisons + rightComparisons;
  }

  public int choosePivotIndex(List<Integer> list, int left, int right) {
    if (PIVOT_METHOD.equals("first") || PIVOT_METHOD.equals("left")) {
      return left;
    } else if (PIVOT_METHOD.equals("last") || PIVOT_METHOD.equals("right")) {
      return right;
    } else {
      // if integer division didn't truncate non-integer results,
      // we would need to get the floor when right - left is even
      final int middleElementIndex = left + ((right - left) / 2);
      return getIndexOfMedian(list, left, middleElementIndex, right);
    }
  }

  public int getIndexOfMedian(List<Integer> list, int leftIndex, int middleIndex, int rightIndex) {
    final int a = list.get(leftIndex);
    final int b = list.get(middleIndex);
    final int c = list.get(rightIndex);
    if ((b <= a && a <= c) || (c <= a && a <= b)) {
      return leftIndex;
    } else if ((a <= b && b <= c) || (c <= b && b <= a)) {
      return middleIndex;
    } else {
      return rightIndex;
    }
  }

  public int partition(List<Integer> list, int left, int right, int pivotIndex) {
    final int pivot = list.get(pivotIndex);
    // As a pre-processing step, swap the pivot with the first element, then we can follow the pseudocode given in the
    // course video
    swap(left, pivotIndex, list);
    int pivotSplitIndex = left + 1;
    for (int scanBoundaryIndex = left + 1; scanBoundaryIndex <= right; scanBoundaryIndex++) {
      if (list.get(scanBoundaryIndex) < pivot) {
        swap(pivotSplitIndex, scanBoundaryIndex, list);
        pivotSplitIndex++;
      }
      if (DEBUG) {
        System.out.println(list + " i = " + pivotSplitIndex + ", j = " + scanBoundaryIndex);
      }
    }
    swap(left, pivotSplitIndex - 1, list);
    return pivotSplitIndex - 1;
  }

  public void swap(int indexA, int indexB, List<Integer> list) {
    if (indexA == indexB) {
      return;
    }
    final int temp = list.get(indexA);
    list.set(indexA, list.get(indexB));
    list.set(indexB, temp);
  }

  public static List<Integer> getRange(int lowerBound, int upperBound) {
    final List<Integer> list = new ArrayList<>();
    for (int i = lowerBound; i < upperBound; i++) {
      list.add(i);
    }
    return list;
  }

  public static List<Integer> getRandomList(int lowerBound, int upperBound) {
    final List<Integer> list = new ArrayList<>();
    while (list.size() < upperBound - lowerBound) {
      final int randomInt = RANDOM.nextInt(upperBound) + lowerBound;
      if (!list.contains(randomInt) && randomInt >= lowerBound && randomInt < upperBound) {
        list.add(randomInt);
      }
    }
    return list;
  }

  private static List<Integer> loadIntegerList() {
    final List<Integer> integerList = new LinkedList<>();
    System.out.println(Paths.get(Resources.getResource("quicksort-number-list.txt")
            .getPath()));
    try (Stream<String> stream = Files.lines(Path.of("C:\\Users\\young\\dev\\algorithms\\src\\main\\resources\\quicksort-number-list.txt"))) {
      stream.forEach(line -> integerList.add(Integer.valueOf(line)));
    } catch (IOException e) {
      System.err.println("Problem reading 'inversion-count-number-list.txt'");
    }
    return integerList;
  }
}
