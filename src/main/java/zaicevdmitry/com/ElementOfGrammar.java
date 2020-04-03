package zaicevdmitry.com;

/**
 * Class for working with determining the type of grammar element
 */
class ElementOfGrammar {
    /**
     * Returns whether the expression is of type Digit
     * @param expression input expression
     */
    static boolean isDigit(String expression) {
        if (expression.length() == 1) {
            return Character.isDigit(expression.charAt(0));
        } else {
            return false;
        }
    }

    /**
     * Returns whether the expression is of type Number
     * @param expression input expression
     */
    static boolean isNumber(String expression) {
        return  isDigit(expression) ||
                ((expression.length()) > 1 && isDigit(String.valueOf(expression.charAt(0))) && isNumber(expression.substring(1)));
    }

    /**
     * Returns whether the expression is of type Operation
     * @param expression input expression
     */
    static boolean isOperation(String expression) {
        return  (expression.equals("+")) || (expression.equals("-")) || (expression.equals("*")) ||
                (expression.equals(">")) || (expression.equals("<")) || (expression.equals("=")) ||
                (expression.equals("&")) || (expression.equals("|"));
    }

    /**
     * Returns whether the expression is of type ConstantExpression
     * @param expression input expression
     */
    static boolean isConstantExpression(String expression) {
        return isNumber(expression) ||
                (expression.length() > 1 && expression.charAt(0) == '-' && isNumber(expression.substring(1)));
    }

    /**
     * Returns whether the operation is of =, & or |
     * @param operation input operation
     */
    private static boolean isCorrectBooleanOperation(String operation) {
        return operation.equals("=") || operation.equals("&") || operation.equals("|");
    }

    /**
     * Returns whether the operation is of =, > or <
     * @param operation input operation
     */
    private static boolean isIncorrectBooleanOperation(String operation) {
        return operation.equals("=") || operation.equals(">") || operation.equals("<");
    }

    /**
     * Returns whether the operation is of +, -, *, >, < or =
     * @param operation input operation
     */
    private static boolean isCorrectArithmeticalOperation(String operation) {
        return operation.equals("+") || operation.equals("-") || operation.equals("*") ||
                operation.equals(">") || operation.equals("<") || operation.equals("=");
    }

    /**
     * Method for expression parsing
     * @param expression input expression
     * @return array of operation, left expression and right expression
     */
    private static String[] getParseExpression(String expression) {
        int i = 0;
        if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            expression = expression.substring(1, expression.length() - 1);
        }
        while ((i < (expression.length())) &&
                !(isExpression(expression.substring(0, i)) &&
                        isOperation(expression.substring(i, i + 1)) &&
                        isExpression(expression.substring(i + 1)))) {
            i++;
        }

        if (i != expression.length()) {
            String operation = expression.substring(i, i + 1);
            String expression1 = expression.substring(0, i);
            String expression2 = expression.substring(i + 1);
            return new String[]{operation, expression1, expression2};
        } else {
            return new String[]{};
        }
    }

    /**
     * Returns whether the left and right expression is of boolean type
     * @param expression input expression
     * @return true if result of left and right expression is true or false, else false
     */
    private static boolean isBooleanExpression(String expression) {

        String[] parsing = getParseExpression(expression);
        if (parsing.length != 0) {
            String operation = parsing[0];
            String expression1 = parsing[1];
            String expression2 = parsing[2];

            if (isExpression(expression1) && !isBinaryExpression(expression1) &&
                    (isIncorrectBooleanOperation(operation) || isCorrectBooleanOperation(operation)) &&
                            isExpression(expression2) && !isBinaryExpression(expression2)) {
                return true;
            }
            return (isCorrectBooleanOperation(operation) && isBooleanExpression(expression1) &&
                    isBooleanExpression(expression2)) ||
                    (isIncorrectBooleanOperation(operation) && isArithmeticalExpression(expression1) &&
                    isArithmeticalExpression(expression2));
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether the left and right expression is of arithmetical type
     * @param expression input expression
     * @return true if result of left and right expression isn't true or false, else false
     */
    private static boolean isArithmeticalExpression(String expression) {
        String[] parsing = getParseExpression(expression);
        if (parsing.length != 0) {
            String operation = parsing[0];
            String expression1 = parsing[1];
            String expression2 = parsing[2];

            if (isExpression(expression1) && !isBinaryExpression(expression1) &&
                    isCorrectArithmeticalOperation(operation) &&
                    isExpression(expression2) && !isBinaryExpression(expression2)) {
                return true;
            }
            return isCorrectArithmeticalOperation(operation) && isArithmeticalExpression(expression1) &&
                    isArithmeticalExpression(expression2);
        }
        else {
            return expression.equals("element") || isConstantExpression(expression);
        }
    }

    /**
     * Returns whether a binary expression is correct
     * @param expression1 input left expression
     * @param operation input operation
     * @param expression2 input right expression
     */
    static boolean isCorrectBinaryExpression(String expression1, String operation, String expression2) {
        if (expression1.length() == 0 || expression2.length() == 0) {
            return false;
        }
        expression1 = expression1.substring(1);
        expression2 = expression2.substring(0, expression2.length() - 1);

        return (isCorrectBooleanOperation(operation) && isBooleanExpression(expression1) && isBooleanExpression(expression2)) ||
                (isCorrectArithmeticalOperation(operation) && isArithmeticalExpression(expression1) && isArithmeticalExpression(expression2));
    }

    /**
     * Returns whether the expression is of type BinaryExpression
     * @param expression
     */
    static boolean isBinaryExpression(String expression) {
        if (expression.length() <= "()".length()) {
            return false;
        }
        if (expression.charAt(0) == '(' && expression.charAt(expression.length() - 1) == ')') {
            int i = 1;
            while ((i < (expression.length() - 1)) &&
                    !(isExpression(expression.substring(1, i)) &&
                            isOperation(expression.substring(i, i + 1)) &&
                            isExpression(expression.substring(i + 1, expression.length() - 1)))) {
                i++;
            }

            if ((i != expression.length()) &&
                    isCorrectBinaryExpression(expression.substring(0,i), String.valueOf(expression.charAt(i)), expression.substring(i + 1))) {
                return i != (expression.length() - 1);
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether the expression is of type Expression
     * @param expression input expression
     */
    static boolean isExpression(String expression) {
        return (expression.equals("element")) ||
                isConstantExpression(expression) ||
                isBinaryExpression(expression);
    }

    /**
     * Returns whether the expression is of type MapCall
     * @param expression input expression
     */
    static boolean isMapCall(String expression) {
        int len = expression.length();
        int lenMap = "map{".length();
        if (len > lenMap) {
            return (expression.substring(0, lenMap).equals("map{")) && (expression.substring(len - 1).equals("}")) &&
                    (isExpression(expression.substring(lenMap, len - 1)));
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether the expression is of type FilterCall
     * @param expression input expression
     */
    static boolean isFilterCall(String expression) {
        int len = expression.length();
        int lenFilter = "filter{".length();
        if (len > lenFilter) {
            return (expression.substring(0, lenFilter).equals("filter{")) && (expression.substring(len - 1).equals("}")) &&
                    (isExpression(expression.substring(lenFilter, len - 1)));
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether the expression is of type Call
     * @param expression input expression
     */
    static boolean isCall(String expression) {

        return isMapCall(expression) || isFilterCall(expression);
    }

    /**
     * Returns whether the expression is of type CallCain
     * @param expression input expression
     */
    static boolean isCallChain(String expression) {
        if (isCall(expression)) {
            return true;
        }
        else {
            int i = 0;
            while ((i < expression.length() - "%>%".length()) &&
                    !((isCall(expression.substring(0, i))) &&
                            (expression.substring(i, i + "%>%".length()).equals("%>%")) &&
                            isCallChain(expression.substring(i + "%>%".length())))) {
                i++;
            }
            return i != expression.length() - "%>%".length();
        }
    }

    /**
     * Returns whether the expression is contains in the grammar
     * @param expression input expression
     */
    static boolean isContainsInGrammar(String expression) {
        return isExpression(expression) ||
                isOperation(expression) ||
                isCallChain(expression);
    }
}