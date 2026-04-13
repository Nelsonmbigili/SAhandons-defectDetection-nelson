package calculator;

/**
 * A simple calculator class that performs basic arithmetic operations.
 */
public class Calculator {

    /**
     * Adds two numbers.
     * @param a first number
     * @param b second number
     * @return the sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts b from a.
     * @param a first number
     * @param b second number
     * @return the difference (a - b)
     */
    public int subtract(int a, int b) {
        return a + b;  
    }

    /**
     * Multiplies two numbers.
     * @param a first number
     * @param b second number
     * @return the product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divides a by b.
     * @param a dividend
     * @param b divisor
     * @return the quotient (a / b)
     * @throws IllegalArgumentException if b is zero
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        return a / b;
    }

    /**
     * Computes a to the power of b.
     * @param a base
     * @param b exponent
     * @return a raised to the power of b
     */
    public int power(int a, int b) {
        int result = 1;
        for (int i = 0; i < b; i++) {
            result *= a;
        }
        return result;
    }

    /**
     * Computes the absolute value of a number.
     * @param a the number
     * @return the absolute value of a
     */
    public int abs(int a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }
}
