public class MathOpNode extends Node{
    //add, subtract, multiply, divide enums
    public enum mathType{
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private static Node left;
    private static Node right;//operands
    private static MathOpNode.mathType mathType;//operators

    MathOpNode(MathOpNode.mathType operators, Node left, Node right) {
        this.left =left;
        this.right = right;
        this.mathType = operators;
    }

    public static Node getLeft(){
        return left;
    }

    public static Node getRight(){
        return right;
    }

    public static mathType getOp() {
        return mathType;
    }

    public static String getOperator(){
        return mathType.toString();
    }

    VariableNode.typeOfVariable getTypeData () throws Exception {
        //left = null, right = null;
        //if either left or right are definable by variables nodes
        /*&& (right instanceof VariableNode)*/
        if (this.left instanceof VariableNode == this.right instanceof VariableNode) {
            VariableNode leftRef = (VariableNode) this.left;
            VariableNode rightRef = (VariableNode) this.right;
        } else {
            throw new Exception("Mismatch or missing syntax");
        }
        //should return left
        return null;
    }

    public static VariableNode.typeOfVariable getMathType() {
        return mathType;
    }

    @Override
    public String toString() {
        return "MATHNODE(" + left.toString() + mathType.toString() + right.toString() + ")";
    }
}
