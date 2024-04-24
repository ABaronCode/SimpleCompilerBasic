import java.nio.file.Path;
import java.util.*;
import java.lang.*;

public class Lexer {

    Token holderTkn;
    LinkedList<Token> tokenStored;
    HashMap<String, Token.TokenType> keywords = new HashMap<>();
    HashMap<String, Token.TokenType> symbols = new HashMap<>();
    HashMap<String, Token.TokenType> twoSymbols = new HashMap<>();
    int lineNum = 1, cPosition = 0;//cPosition shows the position at the start of the token
    StringBuilder sb = new StringBuilder();

    //Passed Path into the Lexer
    public Lexer(Path filePath) throws Exception {
        String passFileName = filePath.toString();
        fillMap();
        //returned linked list is located here printing for debugging purposes
        tokenStored = lex(passFileName);
        System.out.println(tokenStored);
    }

    //This will parse through document text and return in a linked list of Tokens of each type of word/number/line end
     LinkedList<Token> lex(String fileName) throws Exception {
        //variables, since endofline is always the same I initialized the Token upon call for easy access
        LinkedList<Token> listOfToken = new LinkedList<>();
        char testing;
        new CodeHandler(fileName);//initialize constructor in CodeHandler for data manipulation only in CodeHandler
        //Why CodeHandler giving error for instantiation of utility class error without any solution?

        //Job of the while loop identify the data found in CodeHandler and assign it a token to be added to linked list
        while(!CodeHandler.isDone()) {
            testing = CodeHandler.Peek(0);
            if(testing == '\n') {//will produce endofline token
                listOfToken.add(new Token(Token.TokenType.ENDOFLINE, lineNum, cPosition));
                lineNum++;
                cPosition = 0;
                CodeHandler.Swallow(1);
            }else if(testing == '\r') {
                //ignore "carriage return" for now, pass over it
                CodeHandler.Swallow(1);
            }else if(Character.isWhitespace(testing) || testing == '\t') {//space or tab, we just skip over
                //Adjust position in CodeHandler and Lexer
                CodeHandler.Swallow(1);
                cPosition++;
            }else if(Character.isDigit(testing)) {
                Token numberToken = processNumber();
                listOfToken.add(numberToken);
            }else if(Character.isAlphabetic(testing)) {
                Token wordToken = processWord();
                listOfToken.add(wordToken);
            }else if(testing == '"') {
                Token literalToken = handleStringLiteral();//HANDLE STRINGLITERAL TOKENS
                listOfToken.add(literalToken);
            }else if (symbols.containsKey(String.valueOf(testing))) {
                    Token symbolToken = handleSymbol();
                    listOfToken.add(symbolToken);
            }else {
                throw new Exception("The character '" + testing + "' is not recognized, at Line Number: " + lineNum + " and Character Position: " + cPosition + "\nProgram will exit");
            }
         }
         //reached eof, should include one more endofline
         listOfToken.add(new Token(Token.TokenType.ENDOFLINE, lineNum, cPosition));
         return listOfToken;
    }

    //methods for building words and numbers
    public Token processNumber() {
        int lengthOfNumber = 0;
        char toAnalyze = CodeHandler.Peek(lengthOfNumber);//get char at index
        boolean justOneDeci = true, tooManyDeci = true;
        sb = new StringBuilder();
        String digit;

        while (Character.isDigit(toAnalyze) || toAnalyze == '.' && tooManyDeci) {
            if(toAnalyze == '.' && !justOneDeci) {
                tooManyDeci = false;
                lengthOfNumber++;
                toAnalyze = CodeHandler.Peek(lengthOfNumber);
            }else if (toAnalyze == '.') {
                justOneDeci = false;
                sb.append(toAnalyze);
                lengthOfNumber++;
                toAnalyze = CodeHandler.Peek(lengthOfNumber);
            }else {
                sb.append(toAnalyze);
                lengthOfNumber++;
                toAnalyze = CodeHandler.Peek(lengthOfNumber);
            }
        }

        digit = sb.toString();
        holderTkn = new Token(Token.TokenType.NUMBER, digit, lineNum, cPosition);
        CodeHandler.Swallow(lengthOfNumber);
        cPosition = cPosition + lengthOfNumber;

        return holderTkn;
    }

    public Token processWord() {
        int lengthOfWord = 0;
        char toAnalyze = CodeHandler.Peek(lengthOfWord);//get char at index
        boolean endWord = true, labelCheck = false;
        sb = new StringBuilder();
        String word;

        while ((Character.isLetterOrDigit(toAnalyze) || toAnalyze == '_' || toAnalyze == '%' || toAnalyze == '$' || toAnalyze == ':') && endWord) {
            if (toAnalyze == '%' || toAnalyze == '$') {
                endWord = false;
            }else if (toAnalyze == ':') {
                endWord = false;
                labelCheck = true;
            }
            sb.append(toAnalyze);//append the char
            lengthOfWord++;
            toAnalyze = CodeHandler.Peek(lengthOfWord);//get the next char to analyze and move index at same time
        }

        word = sb.toString();
        if (keywords.containsKey(word)) {//handle keywords
            holderTkn = new Token(keywords.get(word), lineNum, cPosition);
        }else if(labelCheck) {//handle labels
            word = word.substring(0, word.length());//add -1 if the desired Label should not have ':' char
            holderTkn = new Token(Token.TokenType.LABEL, word, lineNum, cPosition);
        }else {//handle words
            holderTkn = new Token(Token.TokenType.WORD, word, lineNum, cPosition);
        }
        CodeHandler.Swallow(lengthOfWord);
        cPosition = cPosition + lengthOfWord;//adjust cPosition for next word/number

        return holderTkn;
    }
    //need to adjust to handle escaped string literals
    public Token handleStringLiteral() {
        char buildLiteral = CodeHandler.GetChar(), endLiteral = '"';
        int lengthOfLiteral = 0;
        String builtLiteral;
        sb = new StringBuilder();

        while (buildLiteral != endLiteral) {
            buildLiteral = CodeHandler.Peek(lengthOfLiteral);
            if ((buildLiteral == '\\') && (CodeHandler.Peek(lengthOfLiteral + 1) == '"')) {
                //sb.append(buildLiteral);//do I want to include \" in the stringliteral OR just the "?
                sb.append(CodeHandler.Peek(lengthOfLiteral + 1));
                lengthOfLiteral = lengthOfLiteral + 2;
            }else if(buildLiteral != '"') {
                sb.append(buildLiteral);
                lengthOfLiteral++;
            }
        }
        CodeHandler.Swallow(lengthOfLiteral + 1);//include left " w/ + 1
        cPosition = cPosition + lengthOfLiteral + 1;
        builtLiteral = sb.toString();

        return new Token(Token.TokenType.STRINGLITERAL, builtLiteral, lineNum, cPosition);
    }

    public Token handleSymbol() {
        //since we arrived at here we will always need to get the first character so we can call GetChar
        String singleSymbol = CodeHandler.PeekString(1);
        String checkSymbolType = CodeHandler.PeekString(2);
        Token whatSymbol;

        //check if a double character symbol
        if(twoSymbols.containsKey(checkSymbolType)) {
            whatSymbol = new Token(twoSymbols.get(checkSymbolType), lineNum, cPosition);
            CodeHandler.Swallow(2);
            cPosition = cPosition + 2;
        }else {
            whatSymbol = new Token(symbols.get(singleSymbol), lineNum, cPosition);
            CodeHandler.Swallow(1);
            cPosition++;
        }

        return whatSymbol;
    }

    public void fillMap() {

        //Keywords
        keywords.put("PRINT", Token.TokenType.PRINT);
        keywords.put("READ", Token.TokenType.READ);
        keywords.put("INPUT", Token.TokenType.INPUT);
        keywords.put("DATA", Token.TokenType.DATA);
        keywords.put("GOSUB", Token.TokenType.GOSUB);
        keywords.put("FOR", Token.TokenType.FOR);
        keywords.put("TO", Token.TokenType.TO);
        keywords.put("STEP", Token.TokenType.STEP);
        keywords.put("NEXT", Token.TokenType.NEXT);
        keywords.put("RETURN", Token.TokenType.RETURN);
        keywords.put("IF", Token.TokenType.IF);
        keywords.put("THEN", Token.TokenType.THEN);
        keywords.put("FUNCTION", Token.TokenType.FUNCTION);
        keywords.put("WHILE", Token.TokenType.WHILE);
        keywords.put("END", Token.TokenType.END);
        //Two character symbols
        twoSymbols.put("<>", Token.TokenType.NOTEQUALS);
        twoSymbols.put("<=", Token.TokenType.LESSTHANEQUALS);
        twoSymbols.put(">=", Token.TokenType.GREATERTHANEQUALS);
        //Symbols
        symbols.put("<", Token.TokenType.LESSTHAN);
        symbols.put(">", Token.TokenType.GREATERTHAN);
        symbols.put("=", Token.TokenType.EQUALS);
        symbols.put("(", Token.TokenType.LPAREN);
        symbols.put(")", Token.TokenType.RPAREN);
        symbols.put("+", Token.TokenType.ADD);
        symbols.put("-", Token.TokenType.SUBTRACT);
        symbols.put("*", Token.TokenType.MULTIPLY);
        symbols.put("/", Token.TokenType.DIVIDE);
        symbols.put(",", Token.TokenType.COMMA);//Added in Parser 2
    }

}
