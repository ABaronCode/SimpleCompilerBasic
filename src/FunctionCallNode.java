import java.util.*;

public class FunctionCallNode extends StatementNode{

    private ArrayList<ParameterNode> parameters;
    private String functionName;

    public FunctionCallNode(String functionName, ArrayList<ParameterNode> parameters){
        this.functionName = functionName;
        this.parameters = parameters;
    }

    public String getFunctionName() {
        return functionName;
    }

    public ArrayList<ParameterNode> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        String str = "FunctionCallNode: Function name( " + functionName + "\n Parameters: \n";
        for(ParameterNode parameterCheck : parameters){
            str += parameterCheck.toString() + "\n";
        }
        str += ")";
        return str;
    }
}