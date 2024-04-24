import java.util.ArrayList;

public class SemanticAnalysis {
    public SemanticAnalysis() {
    }

    public void CheckAssignments(ProgramNode programNode) throws Exception {
        // Traverse the abstract syntax tree to find assignment statements
        ArrayList<AssignmentNode> assign = programNode.getProgramNode();
        //call the function
        VariableNode.typeOfVariable nameLeft = null;
        VariableNode.typeOfVariable nameRight = null;
        for (AssignmentNode node : assign) {
            nameLeft = checkLeft(node);
            nameRight = checkRight(node);
            if (nameLeft == null || nameRight == null) {
                throw new Exception("Unknown data type found.");
            } else if (nameLeft != nameRight) {
                throw new Exception("Mistmatched typing: Left(" + nameLeft + ") does not equal right (" + nameRight + ").");
            }
        }

    }

    //checks the current node type
    private VariableNode.typeOfVariable checkLeft(AssignmentNode assignmentNode) throws Exception {
        //as long as variable type is that of one of the 3 variable types it will not throw of exception
        if (!(assignmentNode.getVariableType() instanceof VariableNode.typeOfVariable)) {
            throw new Exception("The left variable is of wrong type.");
        } else {
            //recursive call
            return assignmentNode.getVariableType();
        }
    }

    private VariableNode.typeOfVariable checkRight(AssignmentNode assignmentNode) throws Exception {
        Node check = AssignmentNode.getNodeToDefine();
        //gets specific type of value of the right node
        if (check instanceof MathOpNode math) {
            return MathOpNode.getMathType();
        } else if (check instanceof IntegerNode) {
            return VariableNode.typeOfVariable.INTEGER;
        } else if (check instanceof FloatNode) {
            return VariableNode.typeOfVariable.FLOAT;
        } else if (check instanceof StringNode) {
            return VariableNode.typeOfVariable.STRING;
        }
        return null;
    }
}
