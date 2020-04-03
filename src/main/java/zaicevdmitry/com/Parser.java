package zaicevdmitry.com;

import java.util.ArrayList;

import static zaicevdmitry.com.ElementOfGrammar.*;

/**
 * Class for parsing some expressions
 */
class Parser {
    /**
     * Method for parsing expressions on MapCall and FilterCall rules
     * @param expression input expression
     * @return parsing Call rules
     */
    static ArrayList<String> parseCallChain(String expression) {
        ArrayList<String> parsePart = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < expression.length() - "%>%".length(); i++) {
            if (expression.substring(i, i + "%>%".length()).equals("%>%")) {
                if (isCall(expression.substring(start, i))) {
                    parsePart.add(expression.substring(start, i));
                }
                start = i + "%>%".length();
            }
        }
        if (isCall(expression.substring(start))) {
            parsePart.add(expression.substring(start));
        }

        return parsePart;
    }

    /**
     * Applies Map to all elements in the parse
     * @param parse ArrayList of CallMap and CallFilter rules
     * @return ArrayList of parsing elements using all MapCall rules
     */
    static ArrayList<String> resultMap(ArrayList<String> parse) {
        for (int i = parse.size() - 1; i >= 0; i--) {
            if (isMapCall(parse.get(i))) {
                for (int j = i + 1; j < parse.size(); j++) {
                    String tmp = parse.get(j).replace("element", parse.get(i).substring("map{".length(), parse.get(i).length() - 1));
                    parse.set(j, tmp);
                }
            }
        }
        return parse;
    }

    /**
     * Returns ArrayList of all FilterCall in the parse
     * @param parse ArrayList of FilterCall and MapCall rules
     */
    static ArrayList<String> resultFilter(ArrayList<String> parse) {
        ArrayList<String> result = new ArrayList<>();
        for (String s : parse) {

            if (isFilterCall(s)) {
                result.add(s.substring("filter{".length(), s.length() - 1));
            }
        }
        return result;
    }

    /**
     * Returns converted in  <filter-call> “%>%” <map-call> string
     * @param expression input expression
     * @throws TypeError if input expression isn't Call-Chain type
     * @throws SyntaxError if the input expression does not belong to the grammar
     */
    static String result(String expression) throws TypeError, SyntaxError {
        if (!isCallChain(expression) && isContainsInGrammar(expression)) {
            throw new TypeError("TYPE ERROR");
        } else if (!isContainsInGrammar(expression)) {
            throw new SyntaxError("SYNTAX ERROR");
        }
        ArrayList<String> parse = parseCallChain(expression);
        ArrayList<String> afterMap = resultMap(parse);
        ArrayList<String> filters = resultFilter(afterMap);
        StringBuilder result = new StringBuilder();
        for (String filter : filters) {
            result.append(filter).append("&");
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        } else {
            result.append("element");
        }
        if (afterMap.get(afterMap.size() - 1).substring(0, "map{".length()).equals("map{"))
            return "filter{" + result + "}%>%" + afterMap.get(afterMap.size() - 1);
        else
            return "filter{" + result + "}%>%map{element}";
    }
}