package zaicevdmitry.com;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElementOfGrammarTest {
    @Test
    public void isDigitT() {
        assertTrue(ElementOfGrammar.isDigit("2"));
    }

    @Test
    public void isDigitF() {
        assertFalse(ElementOfGrammar.isDigit("s"));
    }

    @Test
    public void isDigit() {
        assertFalse(ElementOfGrammar.isDigit("123"));
    }

    @Test
    public void isDigitForEmptyExpression() {
        assertFalse(ElementOfGrammar.isDigit(""));
    }

    @Test
    public void isNumberForDigit() {
        assertTrue(ElementOfGrammar.isNumber("2"));
    }

    @Test
    public void isNumberT() {
        assertTrue(ElementOfGrammar.isNumber("20"));
    }

    @Test
    public void isNumberF() {
        assertFalse(ElementOfGrammar.isNumber("2aq"));
    }

    @Test
    public void isNumberForEmptyExpression() {
        assertFalse(ElementOfGrammar.isDigit(""));
    }

    @Test
    public void isOperationT() {
        assertTrue(ElementOfGrammar.isOperation("+"));
    }

    @Test
    public void isOperationF() {
        assertFalse(ElementOfGrammar.isOperation("1sws"));
    }

    @Test
    public void isConstantExpression() {
        assertTrue(ElementOfGrammar.isConstantExpression("123"));
    }

    @Test
    public void isConstantNegativeExpression() {
        assertTrue(ElementOfGrammar.isConstantExpression("-123"));
    }

    @Test
    public void isConstantF() {
        assertFalse(ElementOfGrammar.isConstantExpression("-1cx23"));
    }

    @Test
    public void isConstantEmptyExpression() {
        assertFalse(ElementOfGrammar.isConstantExpression(""));
    }

    @Test
    public void isBinaryExpressionT() throws SyntaxError {
        assertTrue(ElementOfGrammar.isBinaryExpression("(1+2)"));
    }

    @Test
    public void isBinaryExpressionF() throws SyntaxError {
        assertFalse(ElementOfGrammar.isBinaryExpression("(1+2"));
    }

    @Test
    public void isBinaryExpressionEmpty() throws SyntaxError {
        assertFalse(ElementOfGrammar.isBinaryExpression(""));
    }

    @Test
    public void isExpressionElement() throws SyntaxError {
        assertTrue(ElementOfGrammar.isExpression("element"));
    }

    @Test
    public void isExpressionCEx() throws SyntaxError {
        assertTrue(ElementOfGrammar.isExpression("-421"));
    }

    @Test
    public void isExpressionBEx() throws SyntaxError {
        assertTrue(ElementOfGrammar.isExpression("(element+2)"));
    }


    @Test
    public void isExpressionF() throws SyntaxError {
        assertFalse(ElementOfGrammar.isExpression("element++2"));
    }

    @Test
    public void isExpressionEmpty() throws SyntaxError {
        assertFalse(ElementOfGrammar.isExpression(""));
    }

    @Test
    public void isMapCallT() throws SyntaxError {
        assertTrue(ElementOfGrammar.isMapCall("map{element}"));
    }

    @Test
    public void isMapCallWithExpression() throws SyntaxError {
        assertTrue(ElementOfGrammar.isMapCall("map{(element<2)}"));
    }

    @Test
    public void isMapCallWithEmptyExpression() throws SyntaxError {
        assertFalse(ElementOfGrammar.isMapCall("map{}"));
    }

    @Test
    public void isMapCallEmptyExpression() throws SyntaxError {
        assertFalse(ElementOfGrammar.isMapCall(""));
    }

    @Test
    public void isFilterCallT() throws SyntaxError {
        assertTrue(ElementOfGrammar.isFilterCall("filter{(element<2)}"));
    }

    @Test
    public void isFilterCallWithEmptyExpression() throws SyntaxError {
        assertFalse(ElementOfGrammar.isFilterCall("filter{}"));
    }

    @Test
    public void isFilterCallEmptyExpression() throws SyntaxError {
        assertFalse(ElementOfGrammar.isFilterCall(""));
    }

    @Test
    public void isCallMapCall() throws SyntaxError {
        assertTrue(ElementOfGrammar.isCall("map{element}"));
    }

    @Test
    public void isCallFilterCall() throws SyntaxError {
        assertTrue(ElementOfGrammar.isCall("filter{element}"));
    }

    @Test
    public void isCallF() throws SyntaxError {
        assertFalse(ElementOfGrammar.isCall("element*2"));
    }

    @Test
    public void isCallEmpty() throws SyntaxError {
        assertFalse(ElementOfGrammar.isCall(""));
    }

    @Test
    public void isCallChainNotCall() throws SyntaxError {
        assertTrue(
                ElementOfGrammar.isCallChain("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}"));
    }

    @Test
    public void isCallChainCall() throws SyntaxError {
        assertTrue(ElementOfGrammar.isCallChain("filter{(element>10)}"));
    }

    @Test
    public void isCallChainF() throws SyntaxError {
        assertFalse(ElementOfGrammar.isCallChain("1%>%element"));
    }

    @Test
    public void isTrue2() throws SyntaxError {
        assertTrue(
                ElementOfGrammar.isExpression("(element<0)"));
    }
}