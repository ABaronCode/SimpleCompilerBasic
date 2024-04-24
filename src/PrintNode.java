import java.util.ArrayList;
import java.util.List;

//does not derive from Node since the job is purely to store and build list to Print
public class PrintNode extends StatementNode{

    //List of nodes
    private final List<Node> toPrint;

    //initialize List, we will then add things to the list after call
    public PrintNode() {
        toPrint = new ArrayList<>();
    }

    //just to method to call to add to list
    public void AddtoPrintNode(Node toAdd) {
        toPrint.add(toAdd);
    }

    //Since this extends Node a toString method is required
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Node print: toPrint) {
            sb.append(print);
        }
        return sb.toString();
    }
}
