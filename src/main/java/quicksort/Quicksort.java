package quicksort;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class Quicksort {

  private final String choosePivotMethod;
  private final boolean debug = false;

  Quicksort() {
    this.choosePivotMethod = "middle";
  }

  Quicksort(String choosePivotMethod) {
    this.choosePivotMethod = choosePivotMethod;
  }

  public static void main(String[] args) {
    final List<Integer> integerList = loadIntegerList();

  }

  public void sort(List<Integer> integerList, int left, int right) {
    if (right - left == 1) {
      return;
    }

    final int pivotIndex = choosePivot(left, right);
    // partition list around pivot
    partition(integerList, left, right, pivotIndex);
    // sort left
    sort(integerList, left, pivotIndex - 1);
    // sort right
    sort(integerList, pivotIndex + 1, right);
  }

  public int choosePivot(int left, int right) {
    if (choosePivotMethod.equals("first")) {
      return left;
    } else if (choosePivotMethod.equals("last")) {
      return right;
    } else {
      // if integer division didn't truncate non-integer results,
      // we would need to get the floor when right - left is even
      return left + ((right - left) / 2);
    }
  }

  public void partition(List<Integer> list, int left, int right, int pivotIndex) {
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
      if (debug) {
        System.out.println(list + " i = " + pivotSplitIndex + ", j = " + scanBoundaryIndex);
      }
    }
    swap(left, pivotSplitIndex - 1, list);
  }

  public void swap(int indexA, int indexB, List<Integer> list) {
    if (indexA == indexB) {
      return;
    }
    final int temp = list.get(indexA);
    list.set(indexA, list.get(indexB));
    list.set(indexB, temp);
  }

  private static List<Integer> loadIntegerList() {
    final List<Integer> integerList = new LinkedList<>();
    try (Stream<String> stream = Files.lines(Paths.get(Resources.getResource("quicksort-number-list.txt").getPath()))) {
      stream.forEach(line -> integerList.add(Integer.valueOf(line)));
    } catch (IOException e) {
      System.err.println("Problem reading 'inversion-count-number-list.txt'");
    }
    return integerList;
  }
}
