package quicksort;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static quicksort.Quicksort.getRandomList;
import static quicksort.Quicksort.getRange;

public class QuicksortTests {

  @Test
  void should_chooseCorrectPivot() {
    final Quicksort pivotIsFirst = new Quicksort("first");
    final Quicksort pivotIsLast = new Quicksort("last");
    final Quicksort pivotIsMiddle = new Quicksort("middle");
    final List<Integer> list2 = Arrays.asList(0,1);
    final List<Integer> list3 = Arrays.asList(0,1,2);
    final List<Integer> list4 = Arrays.asList(0,1,2,3);

    assertEquals(0, pivotIsFirst.choosePivotIndex(list3, 0, 2));
    assertEquals(0, pivotIsFirst.choosePivotIndex(list4, 0, 3));

    assertEquals(2, pivotIsLast.choosePivotIndex(list3, 0, 2));
    assertEquals(3, pivotIsLast.choosePivotIndex(list4, 0, 3));

    assertEquals(0, pivotIsMiddle.choosePivotIndex(list2, 0, 1));
    assertEquals(1, pivotIsMiddle.choosePivotIndex(list3, 0, 2));
    assertEquals(1, pivotIsMiddle.choosePivotIndex(list4, 0, 3));
  }

  @Test
  void should_chooseCorrectPivot_middlePivot() {
    final Quicksort pivotIsMiddle = new Quicksort("middle");
    final List<Integer> list6 = getRange(0, 6);
    final List<Integer> list2 = getRange(0, 11);

    // [0,1,2,3,4,5] -> 2
    assertEquals(2, pivotIsMiddle.choosePivotIndex(list6,0, 5));
    // [0,1,(2,3,4),5] -> 3
    assertEquals(3, pivotIsMiddle.choosePivotIndex(list6,2, 4));
    // [0,1,(2,3,4,5)] -> 3
    assertEquals(3, pivotIsMiddle.choosePivotIndex(list6,2, 5));
    // [(0,1,2,3,4),5] -> 2
    assertEquals(2, pivotIsMiddle.choosePivotIndex(list6,0, 4));

    // [0,1,2,3,4,5,6,7,8,9,10] -> 5
    assertEquals(5, pivotIsMiddle.choosePivotIndex(list2,0, 10));
    // [0,(1,2,3,4,5,6,7,8,9,10)] -> 5
    assertEquals(5, pivotIsMiddle.choosePivotIndex(list2,1, 10));
    // [0,1,2,3,4,5,6,7,8,9,(10)] -> 10
    assertEquals(10, pivotIsMiddle.choosePivotIndex(list2,10, 10));
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
  void should_partitionList() {
    List<Integer> list = Arrays.asList(3,2,1,4,5);
    final Quicksort quicksort = new Quicksort();
    quicksort.partition(list, 0, list.size() - 1, 0);
    assertEquals(Arrays.asList(1,2,3,4,5), list);
  }

  @Test
  @DisplayName("[3,2,1,4,5] -> [1,2,3,4,5], left pivot")
  void should_sortList1_leftPivot() {
    List<Integer> list = Arrays.asList(3,2,1,4,5);
    final Quicksort quicksortMiddle = new Quicksort("left");

    final int comparisons = quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    assertEquals(6, comparisons, "There should be 4 comparisons.");
  }

  @Test
  @DisplayName("[3,2,1,4,5] -> [1,2,3,4,5], right pivot")
  void should_sortList1_rightPivot() {
    List<Integer> list = Arrays.asList(3,2,1,4,5);
    final Quicksort quicksortMiddle = new Quicksort("right");

    final int comparisons = quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    assertEquals(10, comparisons, "There should be 4 comparisons.");
  }

  @Test
  @DisplayName("[3,2,1,4,5] -> [1,2,3,4,5], middle pivot")
  void should_sortList1_middlePivot() {
    List<Integer> list = Arrays.asList(3,2,1,4,5);
    final Quicksort quicksortMiddle = new Quicksort();

    final int comparisons = quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    assertEquals(6, comparisons, "There should be 6 comparisons.");
  }

  @Test
  @DisplayName("[4,3,2,5,1] -> [1,2,3,4,5]")
  void should_sortList2_middlePivot() {
    List<Integer> list = Arrays.asList(4,3,2,5,1);
    final Quicksort quicksortMiddle = new Quicksort("middle");

    final int comparisons = quicksortMiddle.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    assertEquals(6, comparisons, "There should be 6 comparisons.");
  }

  @Test
  @DisplayName("[1,6,8,10,7,5,2,9,4,3] -> [1,2,3,4,5,6,7,8,9,10]")
  void should_sortList2_leftPivot() {
    List<Integer> list = Arrays.asList(1,6,8,10,7,5,2,9,4,3);
    final Quicksort quicksortLeft = new Quicksort("middle");

    quicksortLeft.sort(list, 0, list.size() - 1);
    assertEquals(Arrays.asList(1,2,3,4,5,6,7,8,9,10), list);
  }

  @Disabled
  @Test
  @DisplayName("Sort 10 lists of 10000 random integers ranging 0 to 10000")
  void should_sortLargeLists() {
    final Quicksort quicksort = new Quicksort();
    final int lowerBound = 0;
    final int upperBound = 10000;
    for (int i = 0; i < 10; i++) {
      final List<Integer> randomList = getRandomList(lowerBound, upperBound);
      System.out.println(randomList.size());
//      System.out.print(randomList + " -> ");
      quicksort.sort(randomList, 0, randomList.size() - 1);
//      System.out.println(randomList);
      assertEquals(randomList, getRange(lowerBound, upperBound));
    }
  }
}
