public class AssignmentNode extends StatementNode {

    private VariableNode.typeOfVariable vNode;
    private static Node toDefine;


    public AssignmentNode(VariableNode.typeOfVariable vNode, Node toDefine) {
        this.vNode = vNode;
        this.toDefine = toDefine;
    }

    public VariableNode.typeOfVariable getVariableType(){
        return vNode;
    }

    public void setNodeToDefine(Node valueIn){
        toDefine = valueIn;
    }

    public static Node getNodeToDefine(){
        return toDefine;
    }

    @Override
    public String toString() {
        return "Assignment Name: (" + vNode + "Expression Name: " + toDefine.toString() + ") ";
    }
}
