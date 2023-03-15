public class VariableNode extends Node {
    private String name;
    public enum variableType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN, ARRAY }
    private variableType type;
    private variableType arrayType;
    private Node value;
    private int isChangeable; //1 if changeable, 0 if not
    private int initialSetSwitch = 0;
    private int intFrom;
    private int intTo;
    private float realFrom;
    private float realTo;

    public VariableNode(String inputName, variableType inputType, int inputChangeable) { //For variables
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
    }

    public VariableNode(String inputName, variableType inputType, int inputChangeable, Node inputValue) { //For constants
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
        value = inputValue;
    }

    public VariableNode(String inputName, variableType inputArrayType, int inputFrom, int inputTo) { //For arrays
        name = inputName;
        type = variableType.ARRAY;
        arrayType = inputArrayType;
        intFrom = inputFrom;
        intTo = inputTo;
    }

    public void setValue(Node inputValue) { //Only works once for constants
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

    public int getChangeable() {
        return isChangeable;
    }
    
    public String ToString() {
        return "VariableNode(" + name + "," + type + ")";
    }    
}
