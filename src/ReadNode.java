import java.util.*;
import java.util.LinkedList;

//goes into printlist, READ takes a list of variables
public class ReadNode extends StatementNode {

    private final List<Node> holdVariables;

    public ReadNode(List<Node> holdVariables) {
        this.holdVariables = holdVariables;
    }

    public List<Node> getReadNode() { return holdVariables; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Node str: holdVariables) {
            sb.append(str);
        }
        return "READNODE(" + sb.toString() + ")";
    }
    //Should I just return the list then handle printing in printlist?
}
