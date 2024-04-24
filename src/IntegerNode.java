public class IntegerNode extends Node{
    private int numberInteger;//holds the integer

    //Constructor to build the Int Node
    public IntegerNode(int numberInt) { this.numberInteger = numberInt; }

    //Accessor
    public int getInteger() { return numberInteger; }

    @Override
    public String toString(){
        return "INTEGERNODE(" + Integer.toString(numberInteger) + ")";
    }
}
