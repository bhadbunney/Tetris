import java.util.Stack;

public class SavableGame {
    public static double evaluate(String expression) {
        String[] tokens = expression.split("\\s+");
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    System.out.println("Invalid expression");
                    return Double.NaN;
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = applyOperator(operand1, operand2, token);
                stack.push(result);
            } else {
                System.out.println("Invalid token: " + token);
                return Double.NaN;
            }
        }

        if (stack.size() != 1) {
            System.out.println("Invalid expression");
            return Double.NaN;
        }

        return stack.pop();
    }

    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/");
    }

    private static double applyOperator(double operand1, double operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    System.out.println("Division by zero error");
                    return Double.NaN;
                }
                return operand1 / operand2;
            default:
                System.out.println("Invalid operator: " + operator);
                return Double.NaN;
        }
    }

    public static void main(String[] args) {
        String expression = "5 1 2 + 4 * + 3 -";  // should evaluate to 14
        double result = evaluate(expression);
        System.out.println("Result: " + result);
    }
}
