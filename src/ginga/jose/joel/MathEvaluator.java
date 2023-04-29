package ginga.jose.joel;

import java.util.*;

public class MathEvaluator {

    public double calculate(String expression) {
        Tokenizer tokenizer =  new Tokenizer();
        Parser parser = new Parser();
        List<String> tokens = tokenizer.tokenize(expression);
        String result = parser.evaluate(tokens);
        return Double.valueOf(result);
    }

    public class Tokenizer{
        private String operations = "+*/-";
        private String minus = "-";
        private String parentheses = "()";
        private String value = "";
        private LinkedList<String> tokens = new LinkedList<>();

        public List<String> tokenize(String expression){
            for(int i=0; i<expression.length(); i++){
                if(expression.charAt(i)==' ')
                    continue;
                char currentChar = expression.charAt(i);

                if(isDelimiter(currentChar)){
                    if(!value.equals(""))
                        tokens.add(value);
                    value = "";
                    if(isNegative(currentChar)){
                        value+=currentChar;
                        continue;
                    }
                    tokens.add(String.valueOf(currentChar));
                    continue;
                }
                value+=currentChar;
            }
            if(!value.equals(""))
                tokens.add(value);
            return tokens;
        }

        private boolean isNegative(char currentChar) {
            return minus.contains(String.valueOf(currentChar)) && (tokens.isEmpty() || operations.contains(tokens.peekLast()));
        }

        private boolean isDelimiter(char currentChar) {
            return operations.contains(String.valueOf(currentChar)) || parentheses.contains(String.valueOf(currentChar));
        }

    }

    public class Parser{
        public String evaluate(List<String> tokens){
            LinkedList<String> temp = new LinkedList<>();
            LinkedList<String> betweenParentheses = new LinkedList<>();
            while(!tokens.isEmpty()){
                String token = tokens.remove(0);

                if(token.equals(")")){
                    String token2 = temp.removeLast();
                    while(!token2.equals("(")){
                        betweenParentheses.add(token2);
                        token2 = temp.removeLast();
                    }
                    Collections.reverse(betweenParentheses);
                    temp.add(calculate(betweenParentheses));
                    betweenParentheses.clear();
                    continue;
                }
                temp.add(token);
            }
            return calculate(temp);
        }

        public String calculate(List<String> tokens){

            tokens = doCalculation(tokens, "/");
            tokens = doCalculation(tokens, "*");
            tokens = doCalculation(tokens, "-");
            tokens = doCalculation(tokens, "+");
            return tokens.get(0);
        }

        public List<String> doCalculation(List<String> tokens, String operation){
            if(!tokens.contains(operation))
                return tokens;
            tokens = checkSigns(tokens);
            LinkedList<String> temp = new LinkedList<>();

            while(!tokens.isEmpty()){
                String token = tokens.remove(0);
                if(token.equals(operation)){
                    String operand1 = temp.removeLast();
                    String operand2 = tokens.remove(0);
                    Double num1 = Double.valueOf(operand1);
                    Double num2 = Double.valueOf(operand2);
                    Double result = doCalculation(num1, num2, operation);
                    temp.add(result.toString());
                    continue;
                }
                temp.add(token);
            }
            return temp;
        }

        private List<String> checkSigns(List<String> tokens) {
            LinkedList<String> temp = new LinkedList<>();
            while(!tokens.isEmpty()){
                String token = tokens.remove(0);
                if(token.equals("-")){
                    if(tokens.isEmpty()){
                        String operand1 = temp.removeLast();
                        Double num1 = -1*Double.valueOf(operand1);
                        temp.add(num1.toString());
                        continue;
                    }
                    if(temp.isEmpty()){
                        String token2 = tokens.remove(0);
                        Double num1 = -1*Double.valueOf(token2);
                        temp.add(num1.toString());
                        continue;
                    }
                    String token2 = temp.removeLast();
                    if("()/*+-".contains(token2)){
                        String operand1 = tokens.remove(0);
                        Double num1 = -1*Double.valueOf(operand1);
                        temp.add(token2);
                        temp.add(num1.toString());
                        continue;
                    }

                    temp.add(token2);
                    temp.add(token);
                    continue;
                }
                temp.add(token);
            }
            return temp;
        }

        private Double doCalculation(Double num1, Double num2, String operation) {
            if("*".equals(operation)){
                return num1.doubleValue()* num2.doubleValue();
            }
            if("/".equals(operation)){
                return num1.doubleValue()/num2.doubleValue();
            }
            if("+".equals(operation)){
                return num1.doubleValue()+num2.doubleValue();
            }
            if("-".equals(operation)) {
                return num1.doubleValue() - num2.doubleValue();
            }
            return null;
        }
    }
}