package quicksort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuicksortTests {

  @Test
  void should_chooseCorrectPivot() {
    List<Integer> list1 = Arrays.asList(0, 10, 20);
    List<Integer> list2 = Arrays.asList(0, 10, 20, 30);

    final Quicksort pivotIsFirst = new Quicksort("first");
    final Quicksort pivotIsLast = new Quicksort("last");
    final Quicksort pivotIsMiddle = new Quicksort("middle");

    assertEquals(0, pivotIsFirst.choosePivot(0, 2));
    assertEquals(0, pivotIsFirst.choosePivot(0, 3));

    assertEquals(2, pivotIsLast.choosePivot(0, 2));
    assertEquals(3, pivotIsLast.choosePivot(0, 3));

    assertEquals(1, pivotIsMiddle.choosePivot(0, 2));
    assertEquals(1, pivotIsMiddle.choosePivot(0, 3));
  }

  @Test
  void should_swapValuesAtTwoIndices() {
    List<Integer> list = Arrays.asList(0, 1, 2, 3);
    final Quicksort quicksort = new Quicksort();

    quicksort.swap(0, 3, list);
    assertEquals(Arrays.asList(3, 1, 2, 0), list);

    quicksort.swap(0, 3, list);
    assertEquals(Arrays.asList(0, 1, 2, 3), list);

    quicksort.swap(1, 3, list);
    assertEquals(Arrays.asList(0, 3, 2, 1), list);
  }

  @Test
  void should_doNothingIfSwappingSameIndices() {
    List<Integer> list = Arrays.asList(0, 1, 2);
    final Quicksort quicksort = new Quicksort();
    quicksort.swap(0, 0, list);
    assertEquals(Arrays.asList(0, 1, 2), list);
  }

  @Test
  void should_partitionEntireList() {
    List<Integer> list = Arrays.asList(1, 3, 5, 2, 4, 6);
    final Quicksort quicksort = new Quicksort();
    quicksort.partition(list, 0, list.size() - 1, 2);
    assertEquals(Arrays.asList(4, 3, 1, 2, 5, 6), list);

    List<Integer> list2 = Arrays.asList(3, 8, 2, 5, 1, 4, 7, 6);
    final Quicksort quicksortFirst = new Quicksort("first");
    quicksortFirst.partition(list2, 0, list2.size() - 1, 0);
    assertEquals(Arrays.asList(1, 2, 3, 5, 8, 4, 7, 6), list2);
  }

  @Test
  @DisplayName("[5,4,3,2,1] -> [1,2,3,4,5], middle pivot")
  void should_sortList1_middlePivot() {
    List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
    // 3,4,5,2,1
    // 3,2,5,4,1
    // 3,2,1,4,5
    // 1,2,3,4,5
    final Quicksort quicksortMiddle = new Quicksort();

    quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
  }

  @Test
  @DisplayName("[5,4,3,2,1] -> [1,2,3,4,5], left pivot")
  void should_sortList1_leftPivot() {
    List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
    final Quicksort quicksortMiddle = new Quicksort("left");

    quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
  }

  @Test
  @DisplayName("[5,4,3,2,1] -> [1,2,3,4,5], right pivot")
  void should_sortList1_rightPivot() {
    List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
    final Quicksort quicksortMiddle = new Quicksort();

    quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
  }
}
