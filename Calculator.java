/**
 * Класс, реализующий простой калькулятор для вычисления арифметических выражений.
 */
import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    /**
     * Метод, который запускает программу.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение:");
        String expression = scanner.nextLine();
        scanner.close();

        try {
            double result = calculate(expression);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Метод, который вычисляет значение арифметического выражения.
     * @param expression - арифметическое выражение для вычисления.
     * @return результат вычисления выражения.
     */
    public static double calculate(String expression) {
        expression = expression.replaceAll("\\s+", "");

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch)) {
                StringBuilder num = new StringBuilder();
                num.append(ch);

                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    num.append(expression.charAt(i + 1));
                    i++;
                }

                numbers.push(Double.parseDouble(num.toString()));
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double result = performOperation(numbers, operators);
                    numbers.push(result);
                }

                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                } else {
                    throw new IllegalArgumentException("Некорректное выражение: непарная скобка");
                }
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek())) {
                    double result = performOperation(numbers, operators);
                    numbers.push(result);
                }

                operators.push(ch);
            } else {
                throw new IllegalArgumentException("Некорректное выражение: недопустимый символ " + ch);
            }
        }

        while (!operators.isEmpty()) {
            double result = performOperation(numbers, operators);
            numbers.push(result);
        }

        if (numbers.size() != 1) {
            throw new IllegalArgumentException("Некорректное выражение: неправильное количество операндов и операторов");
        }

        return numbers.pop();
    }

    /**
     * Метод, который проверяет, является ли символ оператором.
     * @param ch - символ для проверки.
     * @return true, если символ является оператором, иначе false.
     */
    public static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    /**
     * Метод, который возвращает приоритет оператора.
     * @param operator - оператор.
     * @return приоритет оператора.
     */
    public static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Метод, который выполняет операцию над двумя операндами и оператором.
     * @param numbers - стек чисел.
     * @param operators - стек операторов.
     * @return результат операции.
     */
    public static double performOperation(Stack<Double> numbers, Stack<Character> operators) {
        double operand2 = numbers.pop();
        double operand1 = numbers.pop();
        char operator = operators.pop();

        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Деление на ноль");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Некорректный оператор: " + operator);
        }
    }
}