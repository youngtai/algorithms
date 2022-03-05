package inversionCount;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class InversionCount {

  public static class InversionData {
    private final List<Integer> sortedIntegerList;
    private final long inversionCount;

    InversionData(List<Integer> integerList, long inversionCount) {
      this.sortedIntegerList = integerList;
      this.inversionCount = inversionCount;
    }

    long getInversionCount() {
      return inversionCount;
    }

    List<Integer> getSortedIntegerList() {
      return sortedIntegerList;
    }
  }

  public static void main(String[] args) {
    final List<Integer> integerList = loadIntegerList();
    System.out.println("List size: " + integerList.size());

    final InversionData inversionData = sortListAndCountInversions(integerList);
    System.out.printf("Inversion count: %d%n", inversionData.getInversionCount());
    // Should output 2407905288
    System.out.println("Sorted List:");
    System.out.println(inversionData.getSortedIntegerList());

  }

  private static List<Integer> loadIntegerList() {
    final List<Integer> integerList = new LinkedList<>();
    try (Stream<String> stream = Files.lines(Paths.get(Resources.getResource("inversion-count-number-list.txt").getPath()))) {
      stream.forEach(line -> integerList.add(Integer.valueOf(line)));
    } catch (IOException e) {
      System.err.println("Problem reading 'inversion-count-number-list.txt'");
    }
    return integerList;
  }

  public static InversionData sortListAndCountInversions(List<Integer> integerList) {
    final int listSize = integerList.size();
    if (listSize == 1) {
      return new InversionData(integerList, 0);
    }
    // split into left and right
    final int splitIndex = listSize / 2;
    final List<Integer> left = integerList.subList(0, splitIndex);
    final List<Integer> right = integerList.subList(splitIndex, listSize);

    final InversionData leftData = sortListAndCountInversions(left);
    final long leftInversionsCount = leftData.getInversionCount();
    final List<Integer> leftSorted = leftData.getSortedIntegerList();
    final InversionData rightData = sortListAndCountInversions(right);
    final long rightInversionsCount = rightData.getInversionCount();
    final List<Integer> rightSorted = rightData.getSortedIntegerList();
    // merge left and right, count split inversions
    final List<Integer> mergedList = new LinkedList<>();
    long splitInversionsCount = 0;
    int leftIndex = 0;
    int rightIndex = 0;
    for (int i = 0; i < integerList.size(); i++) {
      if (rightSorted.get(rightIndex) < leftSorted.get(leftIndex)) {
        mergedList.add(rightSorted.get(rightIndex));
        rightIndex++;
        splitInversionsCount += leftSorted.subList(leftIndex, leftSorted.size()).size();
        if (rightSorted.size() == rightIndex) {
          mergedList.addAll(leftSorted.subList(leftIndex, leftSorted.size()));
          break;
        }
      } else {
        mergedList.add(leftSorted.get(leftIndex));
        leftIndex++;
        if (leftSorted.size() == leftIndex) {
          mergedList.addAll(rightSorted.subList(rightIndex, rightSorted.size()));
          break;
        }
      }
    }
    return new InversionData(mergedList, leftInversionsCount + rightInversionsCount + splitInversionsCount);
  }

}