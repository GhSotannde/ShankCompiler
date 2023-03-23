public class VariableNode extends Node {
    private String name;
    public enum variableType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN, ARRAY }
    private variableType type;
    private variableType arrayType;
    private Node value = null;
    private boolean isChangeable; //1 if changeable, 0 if not
    private int hasTypeLimit = 0; // 1 if has type limit, 0 if not
    private int initialSetSwitch = 0;
    private int intFrom;
    private int intTo;
    private float realFrom;
    private float realTo;
    private float realArray[];
    private int intArray[];
    private boolean boolArray[];
    private String stringArray[];
    private char charArray[];

    public VariableNode(String inputName, variableType inputType, boolean inputChangeable) { //For variables
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
    }

    public VariableNode(String inputName, variableType inputType, boolean inputChangeable, Node inputValue) { //For constants
        name = inputName;
        type = inputType;
        isChangeable = inputChangeable;
        value = inputValue;
    }

    public VariableNode(String inputName, variableType inputArrayType, int inputFrom, int inputTo, variableType inputType) { //For arrays
        name = inputName;
        type = inputType;
        arrayType = inputArrayType;
        intFrom = inputFrom;
        intTo = inputTo;
        isChangeable = true;
        hasTypeLimit = 1;
        switch (arrayType) {
            case REAL :
                realArray = new float[inputTo - inputFrom];
                break;
            case INTEGER :
                intArray = new int[inputTo - inputFrom];
                break;
            case BOOLEAN :
                boolArray = new boolean[inputTo - inputFrom];
                break;
            case STRING :
                stringArray = new String[inputTo - inputFrom];
                break;
            case CHARACTER :
                charArray = new char[inputTo - inputFrom];
                break;
            default :
                System.out.println("ERROR: No type for array.");
                System.exit(0);
                break;
        }
    }

    public VariableNode(String inputName, variableType inputType, int inputFrom, int inputTo) { //For int and string ranges
        name = inputName;
        type = inputType;
        intFrom = inputFrom;
        intTo = inputTo;
        isChangeable = true;
        hasTypeLimit = 1;
    }

    public VariableNode(String inputName, variableType inputType, float inputRealFrom, float inputRealTo) { //For real ranges
        name = inputName;
        type = inputType;
        realFrom = inputRealFrom;
        realTo = inputRealTo;
        isChangeable = true;
        hasTypeLimit = 1;
    }

    public void setValue(Node inputValue) { //Only works once for constants
        if (isChangeable == true)
            value = inputValue;
        if (isChangeable == false && initialSetSwitch == 0) {
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

    public variableType getArrayType() {
        return arrayType;
    }

    public String getName() {
        return name;
    }

    public boolean getChangeable() {
        return isChangeable;
    }

    public int getIntFrom() {
        return intFrom;
    }

    public int getIntTo() {
        return intTo;
    }

    public float getRealFrom() {
        return realFrom;
    }

    public float getRealTo() {
        return realTo;
    }

    public float[] getRealArray() {
        return realArray;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public boolean[] getBoolArray() {
        return boolArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public char[] getCharArray() {
        return charArray;
    }

    public int getHasTypeLimit() {
        return hasTypeLimit;
    }
    
    public String ToString() {
        return "VariableNode(" + name + "," + type + ")";
    }    
}
