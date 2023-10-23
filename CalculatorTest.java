import org.junit.Test;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testCalculate() {
        String expression1 = "2 + 3";
        double result1 = Calculator.calculate(expression1);
        assertEquals(5, result1, 0.0001);

        String expression2 = "5 - 2";
        double result2 = Calculator.calculate(expression2);
        assertEquals(3, result2, 0.0001);

        String expression3 = "3 * 4";
        double result3 = Calculator.calculate(expression3);
        assertEquals(12, result3, 0.0001);

        String expression4 = "10 / 2";
        double result4 = Calculator.calculate(expression4);
        assertEquals(5, result4, 0.0001);

        String expression5 = "(2 + 3) * 4";
        double result5 = Calculator.calculate(expression5);
        assertEquals(20, result5, 0.0001);

        String expression6 = "3.5 + 2.1";
        double result6 = Calculator.calculate(expression6);
        assertEquals(5.6, result6, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithInvalidExpression() {
        Calculator.calculate("2+2+");
    }

    @Test(expected = ArithmeticException.class)
    public void testCalculateWithDivideByZero() {
        Calculator.calculate("1/0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateWithInvalidCharacter() {
        Calculator.calculate("1+@");
    }
}