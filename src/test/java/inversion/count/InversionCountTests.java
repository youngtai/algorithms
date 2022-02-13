package inversion.count;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InversionCountTests {

  @Test
  void shouldCountInversions1() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(1, 2, 3, 4));
    assertEquals(0, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions2() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(1, 3, 2, 4));
    assertEquals(1, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions3() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(4, 3, 2, 1));
    assertEquals(6, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions4() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(1, 2, 3));
    assertEquals(0, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions5() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(1, 2));
    assertEquals(0, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions6() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(2, 1));
    assertEquals(1, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }

  @Test
  void shouldCountInversions7() {
    List<Integer> integerList = new LinkedList<>(Arrays.asList(1, 3, 5, 2, 4, 6));
    assertEquals(3, InversionCount.sortListAndCountInversions(integerList).getInversionCount());
  }
}
