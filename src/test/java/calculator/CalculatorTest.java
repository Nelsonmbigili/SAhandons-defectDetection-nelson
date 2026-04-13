package calculator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for the Calculator class.
 */
public class CalculatorTest {

    private Calculator calc = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calc.add(2, 3));
        assertEquals(0, calc.add(-1, 1));
        assertEquals(-5, calc.add(-2, -3));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calc.subtract(3, 2));
        assertEquals(-2, calc.subtract(-1, 1));
        assertEquals(1, calc.subtract(-2, -3));
    }

    @Test
    public void testMultiply() {
        assertEquals(6, calc.multiply(2, 3));
        assertEquals(-6, calc.multiply(-2, 3));
        assertEquals(6, calc.multiply(-2, -3));
        assertEquals(0, calc.multiply(0, 5));
    }

    @Test
    public void testDivide() {
        assertEquals(2, calc.divide(6, 3));
        assertEquals(-2, calc.divide(-6, 3));
        assertEquals(0, calc.divide(0, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDivideByZero() {
        calc.divide(5, 0);
    }

    @Test
    public void testPower() {
        assertEquals(8, calc.power(2, 3));
        assertEquals(1, calc.power(5, 0));
        assertEquals(25, calc.power(5, 2));
    }

    @Test
    public void testPowerWithNegativeBase() {
        assertEquals(-8, calc.power(-2, 3));
        assertEquals(4, calc.power(-2, 2));
    }

    @Test
    public void testAbs() {
        assertEquals(5, calc.abs(5));
        assertEquals(5, calc.abs(-5));
        assertEquals(0, calc.abs(0));
    }

    @Test
    public void testAbsWithLargeNumbers() {
        assertEquals(1000, calc.abs(-1000));
        assertEquals(999, calc.abs(999));
    }
}
