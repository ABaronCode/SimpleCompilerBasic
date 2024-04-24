public class Token {

    public TokenType tokenKind;
    public String tokenData;
    public int lineNum, cPosition;//These values should be shared throughout program to stay up to date

    //Enums for lexer, will define each data formed by the lexer
    public enum TokenType{
            WORD, NUMBER, ENDOFLINE, PRINT, READ, INPUT, GOSUB, WHILE,
            FOR, TO, STEP, NEXT, RETURN, IF, THEN, FUNCTION, END, DATA,
            NOTEQUALS, LESSTHANEQUALS, GREATERTHANEQUALS, LESSTHAN, GREATERTHAN,
            EQUALS, LPAREN, RPAREN, ADD, SUBTRACT, MULTIPLY, DIVIDE, LABEL, STRINGLITERAL, COMMA//Added in Parser 2
    }
    //Constructor w/o data from Basic File (e.g. ENDOFLINE)
    public Token (TokenType kind, int lNum, int cPos) {
        tokenKind = kind;
        tokenData = null;
        lineNum = lNum;
        cPosition = cPos;
    }
    //Constructor w/ data from BASIC file (e.g. NUMBER and WORD Tokens)
    public Token (TokenType kind, String data, int lNum, int cPos) {
        tokenKind = kind;
        tokenData = data;//this is the value that contains data from the txt file
        lineNum = lNum;
        cPosition = cPos;
    }

    @Override
    public String toString() {
        if(tokenData == null) {
            return tokenKind + "";
        }else {
            return tokenKind + "(" + tokenData + ")";
        }
    }
}
