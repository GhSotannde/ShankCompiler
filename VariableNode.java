public class VariableNode extends Node {
    private String name;
    public enum variableType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN, ARRAY }
    private variableType type;
    private Node value;
    private int changeable; //1 if changeable, 0 if not
    private int from;
    private int to;

    public VariableNode(String inputName, variableType inputType, Node inputValue, int inputChangeable) {
        name = inputName;
        type = inputType;
        value = inputValue;
        changeable = inputChangeable;
    }

    public void setValue(Node inputValue) {
        if (changeable == 1)
            value = inputValue;
    }

    public Node getValue() {
        return value;
    }

    public variableType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
    
    public String ToString() {
        return "VariableNode(" + name + "," + type + ")";
    }    
}
