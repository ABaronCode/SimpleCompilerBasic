import java.util.Optional;

public class LabledStatementNode extends StatementNode {

    private final String label;
    private final StatementNode referenceNode;

    public LabledStatementNode(String label, StatementNode nodeToReference) {
        this.label = label;
        this.referenceNode = nodeToReference;
    }


    //if I need to retrieve one or the other
    public String getLabel () {
        return label;
    }

    public StatementNode getReferenceNode () {
        return referenceNode;
    }


    @Override
    public String toString() {
        return "LABELEDSTATEMENTNODE(" + label + ")";
    }
}
