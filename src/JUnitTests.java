import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Nested;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

//LEXER 1: Make Junit tests for Lexer and CodeHandler, Test with multi-line strings. Test with words then numbers and numbers and then words
public class JUnitTests {
    //Assignment 1: Lexer

    //checks to see if the number of lines will equal the expected amount
    @Test
    public void lexerLineNumTest() throws Exception {
        Path pt = Path.of("src/LinesForLexer.bas");
        var lxr = new Lexer(pt);
        //how many lines
        Assert.assertTrue(lxr.lineNum == 17);//confirms it goes through all lines
    }

    //check if token types will output correct kinds back to be added to LL
    @Test
    public void lexerWordTest() throws Exception {
        Path pt = Path.of("src/LinesForLexer.bas");
        var lxr = new Lexer(pt);

        Token isWord = lxr.processWord();
        Token isNumber = lxr.processNumber();
        Assert.assertEquals(Token.TokenType.WORD, isWord.tokenKind);
        Assert.assertEquals(Token.TokenType.NUMBER, isNumber.tokenKind);
    }

    //looks to see if the code handler peek method is working correctly on the string
    @Test
    public void codeHandlerPeekTest() throws IOException {
        String fName = "src/LinesForLexer.bas";
        CodeHandler cd = new CodeHandler(fName);

        char rString = CodeHandler.Peek(3);
        Assert.assertEquals('h', rString);
    }

    @Test
    public void codeHandlerIsDoneTest() throws IOException {
        String fName = "src/LinesForLexer.bas";
        CodeHandler cd = new CodeHandler(fName);
        var mSize = Files.readAllBytes(Path.of(fName)).length;//max length currently 57

        CodeHandler.Swallow(mSize);//Swallows max length

        //CodeHandler checks if index is greater than or equal to the length of the string
        //If it returns true then that means that the CodeHandler index has completely parsed through the document
        Assert.assertTrue(CodeHandler.isDone());
    }

    //Assignment 2: Finish Lexer

    //Check if the string returns a string of size 13
    @Test
    public void codeHandlerPeekStringTest() throws IOException {
        String fName = "src/LinesForLexer.bas";
        new CodeHandler(fName);
        var line = CodeHandler.PeekString(13);

        Assert.assertEquals(13, line.length());
    }
    //checks to see if the getChar will return the right character back in this test it test the third  character
    @Test
    public void codeHandlerGetCharTest() throws IOException {
        String fName = "src/LinesForLexer.bas";
        CodeHandler cd = new CodeHandler(fName);
        var toCheck = CodeHandler.Peek(3);
        for(int x = 0; x < 2; x++) {
            CodeHandler.GetChar();
        }
        Assert.assertEquals(toCheck, CodeHandler.GetChar());
    }

    //check to see if remainder works and the gives a remaining string that is smaller than the original string of data
    @Test
    public void codeHandlerRemainderTest () throws Exception {
        String fname = "src/LinesForLexer.bas";
        new CodeHandler(fname);
        int completeSize = CodeHandler.Remainder().length();
        CodeHandler.Swallow(10);
        var testSize = CodeHandler.Remainder().length();

        Assert.assertTrue(testSize < completeSize);
    }

    //Test token output system
    @Test
    public void tokenPrintOutTest() {
        var data = new Token(Token.TokenType.WORD, "Hello", 1, 1);
        String shouldPrintOut = "WORD(Hello)";

        Assert.assertEquals(data.toString(), shouldPrintOut);
    }

    //PARSER 1
    @Test
    public void factorTest() {

    }

    @Test
    public void parserTest() {

    }

    @Test
    public void termTest() {

    }


    @Test
    public void acceptSeperatorTest() {
        LinkedList<Token> hasEnd = new LinkedList<>();
        Token number = new Token(Token.TokenType.NUMBER, "5", 1, 1);
        Token numberTwo = new Token(Token.TokenType.NUMBER, "5", 1, 3);
        Token math = new Token(Token.TokenType.ADD, "+", 1, 2);
        Token end = new Token(Token.TokenType.ENDOFLINE, 2, 0);
        hasEnd.add(number);
        hasEnd.add(math);
        hasEnd.add(numberTwo);
        hasEnd.add(end);
        hasEnd.add(number);

        Parser pr = new Parser(hasEnd);


        Assert.assertTrue(pr.AcceptSeparators());

    }

    @Test
    public void peekTest() {
        LinkedList<Token> toPeek = new LinkedList<>();
        Token number = new Token(Token.TokenType.NUMBER, "5", 1, 1);
        Token numberTwo = new Token(Token.TokenType.NUMBER, "5", 1, 3);
        Token math = new Token(Token.TokenType.ADD, "+", 1, 2);
        toPeek.add(number);
        toPeek.add(math);
        toPeek.add(numberTwo);

        TokenManager tk = new TokenManager(toPeek);
        Token.TokenType check = Token.TokenType.NUMBER;

        Assert.assertEquals(check, tk.Peek(2).get().tokenKind);
    }

    @Test
    public void matchAndRemoveTest() {
        LinkedList<Token> toRemove = new LinkedList<>();
        Token number = new Token(Token.TokenType.NUMBER, "5", 1, 1);
        Token numberTwo = new Token(Token.TokenType.NUMBER, "5", 1, 3);
        Token math = new Token(Token.TokenType.ADD, "+", 1, 2);
        toRemove.add(number);
        toRemove.add(math);
        toRemove.add(numberTwo);

        TokenManager tk = new TokenManager(toRemove);
        tk.MatchAndRemove(Token.TokenType.NUMBER);
        tk.MatchAndRemove(Token.TokenType.ADD);
        tk.MatchAndRemove(Token.TokenType.NUMBER);
        Assert.assertTrue(toRemove.isEmpty());
    }

    //PARSER 2 TESTS
    @Test
    public void StatementTest() {

    }

    @Test
    public void PrintStatementTest() {

    }

    @Test
    public void PrintListTest() {

    }

    @Test
    public void StatementSTest() {

    }

    @Test
    public void AssignmentTest() {

    }

    @Test
    public void printTest() {
        ArrayList<Node> checkPrint = null;
        IntegerNode number = new IntegerNode(5);
        IntegerNode numberTwo = new IntegerNode(3);
        MathOpNode math = new MathOpNode(MathOpNode.mathType.ADD, number, numberTwo);
        Token comma = new Token(Token.TokenType.COMMA, "," , 1 ,4);
        //VariableNode vari = new VariableNode();
        //checkPrint.add(number);
        //checkPrint.add(comma);
        //checkPrint.add(numberTwo);
        //checkPrint.add(comma);
        //ScheckPrint.add(number);



        //Add call and check StatementsNode to contain a Variable Node @ the 4th value in list
    }

    //5: EVEN MORE PARSER
    
    @Test
    //checks that all tokens are added to the AST
    public void parsTokenTest() {
        Optional<Token> number = Optional.of(new Token(Token.TokenType.NUMBER, "5", 1, 1));
        Optional<Token> numberTwo = Optional.of(new Token(Token.TokenType.NUMBER, "5", 1, 3));
        Optional<Token> math = Optional.of(new Token(Token.TokenType.ADD, "+", 1, 2));
        ASTNode checker = new ASTNode(math, numberTwo, number);

        System.out.println(checker.toString());
        //ensure I can fill a ASTNODE with items for the statements to process
        Assert.assertTrue(checker != null);

    }


    //Start the interpretor tests(Assignment 7)

    //More Interpretor tests(Assignment 8)


}
