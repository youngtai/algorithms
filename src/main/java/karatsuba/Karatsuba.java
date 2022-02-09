package karatsuba;

import java.math.BigInteger;

public class Karatsuba {

  public static void main(String[] args) {
    final int input = 1234567;  // 1234, 567
    final int base10Places = String.valueOf(input).length() / 2;
    final int firstHalf = (int) Math.floor(input / Math.pow(10, base10Places));
    final int secondHalf = Math.floorMod(input, (int) Math.pow(10, base10Places));

    System.out.println("input: " + input);
    System.out.println("split index: " + base10Places);
    System.out.println("first half: " + firstHalf);
    System.out.println("second half: " + secondHalf);
  }

  public static BigInteger applyKaratsuba(BigInteger x, BigInteger y) {
    if (String.valueOf(x).length() == 1 || String.valueOf(y).length() == 1) {
      return x.multiply(y);
    }

    final int n2 = getBaseTenLength(x, y) / 2;

    // Split x into a, b, where a is first floor(n / 2) digits, b is remaining digits
    final BigInteger a = getFirstHalfOfDigits(x, n2);
    final BigInteger b = getSecondHalfOfDigits(x, n2);
    // Same for y, into c, d
    final BigInteger c = getFirstHalfOfDigits(y, n2);
    final BigInteger d = getSecondHalfOfDigits(y, n2);

    // then x*y = 10^n(ac) + 10^(n/2)(ad+bc) + bd
    final BigInteger ac = applyKaratsuba(a, c);
    final BigInteger bd = applyKaratsuba(b, d);
    // Compute middle term, (ad+bc) using Gauss's trick (a+b)(c+d)=ac+ad+bc+bd
    final BigInteger intermediateProduct = applyKaratsuba(a.add(b), c.add(d));
    final BigInteger middleTerm = (intermediateProduct.subtract(ac)).subtract(bd);

    final BigInteger firstProduct = BigInteger.TEN.pow(n2 * 2).multiply(ac);
    final BigInteger secondProduct = BigInteger.TEN.pow(n2).multiply(middleTerm);

    return firstProduct.add(secondProduct).add(bd);
  }

  static int getBaseTenLength(BigInteger x, BigInteger y) {
    return Math.max(String.valueOf(x).length(), String.valueOf(y).length());
  }

  static BigInteger getFirstHalfOfDigits(BigInteger input, int length) {
    return input.divide(new BigInteger("1" + "0".repeat(Math.max(0, length))));
  }

  static BigInteger getSecondHalfOfDigits(BigInteger input, int length) {
    return input.mod(new BigInteger("1" + "0".repeat(Math.max(0, length))));
  }

}
