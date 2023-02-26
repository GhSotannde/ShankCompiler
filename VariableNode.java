public class VariableNode extends Node {
    private String name;
    public enum variableType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN, ARRAY }
    private variableType type;
    private Node value;
    private int isChangeable; //1 if changeable, 0 if not
    private int initialSetSwitch = 0;
    private int from;
    private int to;

    public VariableNode(String inputName, variableType inputType, int inputChangeable) {
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
    }

    public VariableNode(String inputName, variableType inputType, int inputChangeable, Node inputValue) {
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
        value = inputValue;
    }

    public void setValue(Node inputValue) {
        if (isChangeable == 1)
            value = inputValue;
        if (isChangeable == 0 && initialSetSwitch == 0) {
            value = inputValue;
            initialSetSwitch = 1;
        }
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
