public class IntegerNode extends StatementNode {

    private int value;

    public IntegerNode(int inputValue) {
        value = inputValue;
    } 

    public int getValue() {
        return value;
    }

    public String ToString() {
        return "IntegerNode(" + value + ")";
    }
    
}
