import java.util.Optional;

//needs to extend node for enums
public class VariableNode extends Node {

    Optional<VariableNode> VariableString;
    enum typeOfVariable {INTEGER, FLOAT, STRING }
    private String strName;
    private Node nonStringValue;
    private typeOfVariable type;


    //Takes the Token makes it a Node and holds the String name in a String inside the Node
    public VariableNode(Optional<VariableNode> in) {
        this.VariableString = in;
    }
    public Optional<VariableNode> VariableNode() { return VariableString; }

    @Override
    public String toString() {
        return "LABELNODE(" + VariableString + ")";
    }
}
