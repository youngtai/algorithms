package karatsuba;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static karatsuba.Karatsuba.getBaseTenLength;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KaratsubaTests {

  @Test
  @DisplayName("2 * 3 = 6")
  void should_multiplySingleDigitNumbers() {
    final BigInteger x = new BigInteger("2");
    final BigInteger y = new BigInteger("3");
    assertEquals(new BigInteger("6"), Karatsuba.applyKaratsuba(x, y), "2 * 3 should equal 6");
  }

  @Test
  @DisplayName("20 * 300 = 6000")
  void should_multiplyNumbers_withZeros() {
    final BigInteger x = new BigInteger("20");
    final BigInteger y = new BigInteger("300");
    assertEquals(new BigInteger("6000"), Karatsuba.applyKaratsuba(x, y), "20 * 300 should equal 6000");
  }

  @Test
  @DisplayName("10 * 10 = 100")
  void should_multiplyTwoDigitNumbers() {
    final BigInteger x = new BigInteger("10");
    final BigInteger y = new BigInteger("10");
    assertEquals(new BigInteger("100"), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  @DisplayName("123 * 456 = 56088")
  void should_multiplyThreeDigitNumbers() {
    final BigInteger x = new BigInteger("123");
    final BigInteger y = new BigInteger("456");
    assertEquals(x.multiply(y), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_multiplyFourDigitNumbers_1000() {
    final BigInteger x = new BigInteger("1000");
    final BigInteger y = new BigInteger("1000");
    assertEquals(new BigInteger("1000000"), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_multiplyEightDigitAndTwoDigitNumbers() {
    final BigInteger x = new BigInteger("12345678");
    final BigInteger y = new BigInteger("56");
    assertEquals(x.multiply(y), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_multiplyFiveDigitAndThreeDigitNumbers() {
    final BigInteger x = new BigInteger("12345");
    final BigInteger y = new BigInteger("678");
    assertEquals(x.multiply(y), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_multiplySixDigitNumbers() {
    final BigInteger x = new BigInteger("123456");
    final BigInteger y = new BigInteger("987654");
    assertEquals(x.multiply(y), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_multiplyBigNumbers() {
    final BigInteger x = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
    final BigInteger y = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");

    final int n = getBaseTenLength(x, y);
    final int n2 = n / 2;

    assertEquals(new BigInteger("31415926535897932384626433832795"), Karatsuba.getFirstHalfOfDigits(x, n2), "first half of x is wrong");
    assertEquals(new BigInteger("02884197169399375105820974944592"), Karatsuba.getSecondHalfOfDigits(x, n2), "second half of x is wrong");
    assertEquals(new BigInteger("27182818284590452353602874713526"), Karatsuba.getFirstHalfOfDigits(y, n2), "first half of y is wrong");
    assertEquals(new BigInteger("62497757247093699959574966967627"), Karatsuba.getSecondHalfOfDigits(y, n2), "second half of y is wrong");

    assertEquals(x.multiply(y), Karatsuba.applyKaratsuba(x, y));
    assertEquals(new BigInteger("8539734222673567065463550869546574495034888535765114961879601127067743044893204848617875072216249073013374895871952806582723184"), Karatsuba.applyKaratsuba(x, y));
  }

  @Test
  void should_getFirstHalfAndSecondHalfOfDigits_evenInput() {
    final BigInteger input = new BigInteger("1234");
    assertEquals(new BigInteger("12"), Karatsuba.getFirstHalfOfDigits(input, 2));
    assertEquals(new BigInteger("34"), Karatsuba.getSecondHalfOfDigits(input, 2));
  }

  @Test
  void should_getFirstHalfAndSecondHalfOfDigits_oddInput() {
    final BigInteger input = new BigInteger("123");
    assertEquals(new BigInteger("12"), Karatsuba.getFirstHalfOfDigits(input, 1));
    assertEquals(new BigInteger("3"), Karatsuba.getSecondHalfOfDigits(input, 1));

    final BigInteger input2 = new BigInteger("12345");
    assertEquals(new BigInteger("123"), Karatsuba.getFirstHalfOfDigits(input2, 2));
    assertEquals(new BigInteger("45"), Karatsuba.getSecondHalfOfDigits(input2, 2));
  }
}
