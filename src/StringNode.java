//StringLiterals

public class StringNode extends StatementNode {
    private final String stringOfLiteral;

    public StringNode (String stringOfLiteral) {
        this.stringOfLiteral = stringOfLiteral;
    }
    public String getString() {
        return stringOfLiteral;
    }

    @Override
    public String toString() {
        return "STRINGNODE(" + stringOfLiteral + ")" ;
    }
}
