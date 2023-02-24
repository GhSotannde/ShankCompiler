public class IntegerNode extends Node {

    private int value;

    public IntegerNode(int inputValue) {
        value = inputValue;
    } 

    public int getValue() {
        return value;
    }

    public void setValue(int inputValue) {
        value = inputValue;
    }

    public String ToString() {
        return "IntegerNode(" + value + ")";
    }
    
}
