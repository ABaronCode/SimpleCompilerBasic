import java.util.ArrayList;

public class FunctionNode {

    private final VariableNode.typeOfVariable functionName;
    private final ArrayList<VariableNode> parameterList;
    private ArrayList<VariableNode> variables;
    private ArrayList<StatementNode> statements;


    public FunctionNode(VariableNode.typeOfVariable functionName, ArrayList<VariableNode> paramList) {
        this.functionName = functionName;
        this.parameterList = paramList;
    }

    VariableNode.typeOfVariable getFunctionName() {
        return functionName;
    }
    ArrayList<VariableNode> getParameterList() {
        return parameterList;
    }

    public ArrayList<VariableNode> getVariables() {
        return variables;
    }

    public ArrayList<StatementNode> getStatements() {
        return statements;
    }
}
