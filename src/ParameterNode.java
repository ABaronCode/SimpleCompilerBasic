public class ParameterNode extends StatementNode {

    private VariableNode.typeOfVariable variableType;
    private Node singleExpression;
    private boolean couldChangeType;

    public ParameterNode(VariableNode.typeOfVariable variableType, Node singleExpression) {
        this.variableType = variableType;
        this.singleExpression = singleExpression;
    }

    public ParameterNode (Node expr) {
        this.singleExpression = expr;
        this.variableType = null;
    }
    public boolean isCouldChangeType() {
        return couldChangeType;
    }

    @Override
    public String toString() {
        String buildParam = "ParameterNode (";
        if (variableType != null) {
            buildParam += variableType;
        }
        buildParam += ")";
        return buildParam;
    }
}
