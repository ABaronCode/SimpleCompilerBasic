import java.util.Optional;

public class ASTNode extends StatementNode {

    private final Optional<Token> operator;
    private final Optional<Token> left;
    private final Optional<Token> right;

    public ASTNode(Optional<Token> operator, Optional<Token> left, Optional<Token> right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }


    @Override
    public String toString() {
        return "AST(" + operator + "," + left + ","  + right + ")";
    }
}
