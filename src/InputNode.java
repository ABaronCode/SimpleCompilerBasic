import java.beans.Statement;
import java.util.List;

//since we dont know the list type of input we use list as it encompasses what is put in
public class InputNode extends StatementNode {

    private final List<String> allVariables;
    private String preDefined;
    private VariableNode isVariable;

    //we dont need to worry about what is
    InputNode (List<String> allVariables) { this.allVariables = allVariables; }

    public List<String> getPreDefined() {
        return allVariables;
    }

    @Override
    public String toString() {
        return "INPUTNODE(" + allVariables + ")";
     }
}
