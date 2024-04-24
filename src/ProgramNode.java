import java.util.*;

public class ProgramNode extends Node{
    private HashMap<String, FunctionNode> functionNodeMap = new HashMap<>();
    private ArrayList<AssignmentNode> nodeArray = new ArrayList<>();
    //this is a Node class which will hold our binary search tree in a LL
    private final ArrayList<AssignmentNode> orderOfOperation;

    ProgramNode(ArrayList<AssignmentNode> orderOfOperation) {
        this.orderOfOperation = orderOfOperation;
    }

    ArrayList<AssignmentNode> getProgramNode() { return orderOfOperation; }


    //need to go through all the nodes and turn them into strings
    @Override
    public String toString() {
        return orderOfOperation.toString();
    }

}
