public class FloatNode extends Node{
    private final float numberFloat;

    //Constructor to build the float
    public FloatNode(float numberFloat) { this.numberFloat = numberFloat; }

    //Accessor
    public float getNumberFloat() { return numberFloat; }

    @Override
    public String toString(){
        return "FLOATNODE(" + Float.toString(numberFloat) + ")";
    }
}
