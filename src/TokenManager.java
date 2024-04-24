import java.util.LinkedList;
import java.util.Optional;

public class TokenManager {
    private final LinkedList<Token> tokenList;

    public TokenManager(LinkedList<Token> in) {
        this.tokenList = in;
    }

    //As long as we do not go past length of list, we can return the value at the point we are requesting
    Optional<Token> Peek(int j) {
        if(j < tokenList.size()) {
            return Optional.of(tokenList.get(j));//token to return
        }else {
            return Optional.empty();//since we are beyond the bounds of the list we return empty option back
        }
    }

    //returns true if the token list is not empty
    boolean MoreTokens() {
        if(tokenList.isEmpty()) {
            return false;//empty list
        }else return true;//still has contents
    }

    //No accessor for Token list
    Optional<Token> MatchAndRemove(Token.TokenType t) {
        Token passedType = tokenList.get(0);
        if(passedType.tokenKind.equals(t)) {
            tokenList.remove();//removes the first value from list
            return Optional.of(passedType);
        }else return Optional.empty();//no match
    }

}