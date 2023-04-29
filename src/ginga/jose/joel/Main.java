package ginga.jose.joel;

public class Main {
    public static void main(String[] args) {
    MathEvaluator evaluator = new MathEvaluator();
        Double result = evaluator.calculate("(123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) - (123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) + (13 - 2)/ -(-11) ");
        System.out.println(result);
        result = evaluator.calculate("(123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) - (123.45*(678.90 / (-2.5+ 11.5)-(((80 -(19))) *33.25)) / 20) ");
        System.out.println(result);
    }
}