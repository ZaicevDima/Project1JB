package zaicevdmitry.com;

import org.junit.Test;
import zaicevdmitry.com.Parser;
import zaicevdmitry.com.SyntaxError;
import zaicevdmitry.com.TypeError;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void parseCallChainTrue() throws SyntaxError {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("map{(element+10)}");
        expected.add("filter{(element>10)}");
        expected.add("map{(element*element)}");
        assertEquals(expected, Parser.parseCallChain("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}"));
    }

    @Test
    public void parseCallChainCallTest() throws SyntaxError {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("map{(element+10)}");
        assertEquals(expected, Parser.parseCallChain("map{(element+10)}"));
    }


    @Test
    public void resultMap() throws SyntaxError {
        ArrayList<String> expected = new ArrayList<>();
        //System.out.println(Parser.resultMap(Parser.parseCallChain("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}")));
        expected.add("map{(element+10)}");
        expected.add("filter{((element+10)>10)}");
        expected.add("map{((element+10)*(element+10))}");
        assertEquals(expected, Parser.resultMap(Parser.parseCallChain("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}")));
    }

    @Test
    public void resultFilter() throws SyntaxError {
        ArrayList<String> expected = new ArrayList<>();
        expected.add("(element>20)");
        expected.add("(element>10)");
        assertEquals(expected, Parser.resultFilter(Parser.parseCallChain("filter{(element>20)}%>%filter{(element>10)}%>%map{(element*element)}")));
    }

    @Test
    public void resultFilterEmpty() throws SyntaxError {
        ArrayList<String> expected = new ArrayList<>();
        assertEquals(expected, Parser.resultFilter(Parser.parseCallChain("map{(element>20)}%>%map{(element>10)}%>%map{(element*element)}")));
    }

    @Test
    public void resultTest1() throws TypeError, SyntaxError {
        assertEquals("filter{((element+10)>10)}%>%map{((element+10)*(element+10))}",
                Parser.result("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}"));
    }

    @Test
    public void resultTest2() throws TypeError, SyntaxError {
        assertEquals("filter{(element>10)&(element<20)}%>%map{element}",
                Parser.result("filter{(element>10)}%>%filter{(element<20)}"));
    }

    @Test
    public void resultTest3() throws TypeError, SyntaxError {
        assertEquals("filter{(element>0)&(element<0)}%>%map{(element*element)}",
                Parser.result("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}"));
    }

    @Test
    public void resultTest4() throws TypeError, SyntaxError {
        assertEquals("filter{(element>0)}%>%map{element}",
                Parser.result("filter{(element>0)}"));
    }

    @Test
    public void resultTest5() throws TypeError, SyntaxError {
        assertEquals("filter{element}%>%map{((element+10)*2)}",
                Parser.result("map{(element+10)}%>%map{(element*2)}"));
    }

    @Test
    public void resultTest6() throws TypeError, SyntaxError {
        assertEquals("filter{(element>10)&(element>2)}%>%map{element}",
                Parser.result("filter{(element>10)}%>%filter{(element>2)}"));
    }

    @Test
    public void resultTest7() throws TypeError, SyntaxError {
        assertEquals("filter{((((element+10)*2)-1)>2)}%>%map{element}",
                Parser.result("map{(((element+10)*2)-1)}%>%filter{(element>2)}"));
    }

    @Test
    public void resultTest8() throws TypeError, SyntaxError {
        assertEquals("filter{(((element>10)&(4=1))|(element<0))&(element>2)}%>%map{element}",
                Parser.result("filter{(((element>10)&(4=1))|(element<0))}%>%filter{(element>2)}"));
    }

    @Test
    public void resultTest9() throws TypeError, SyntaxError {
        assertEquals("filter{(((element-10)>(4*element))&(element>10))&(element>2)}%>%map{element}",
                Parser.result("filter{(((element-10)>(4*element))&(element>10))}%>%filter{(element>2)}"));
    }

    @Test (expected= TypeError.class)
    public void resultTypeException() throws TypeError, SyntaxError {
       Parser.result("(element>0)");
    }

    @Test (expected= SyntaxError.class)
    public void resultSyntaxException() throws TypeError, SyntaxError {
        Parser.result("((element>0)");
    }

    @Test (expected= SyntaxError.class)
    public void resultSyntaxExceptionInParse() throws TypeError, SyntaxError {
        Parser.result("filter{element>0)}%>%map{element}");
    }

    @Test (expected= SyntaxError.class)
    public void resultSyntaxExceptionInParse2() throws TypeError, SyntaxError {
        Parser.result("filter{(element>0)}%>%map{(element}");
    }

    @Test (expected= SyntaxError.class)
    public void resultSyntaxExceptionInParse3() throws TypeError, SyntaxError {
        Parser.result("filter{(((element+10)-(4*element))&(element>10))}%>%filter{(element>2)}");
    }
}