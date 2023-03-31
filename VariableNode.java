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

    public VariableNode(String inputName, int inputFrom, int inputTo, variableType inputArrayType) { //For arrays
        name = inputName;
        type = VariableNode.variableType.ARRAY;
        arrayType = inputArrayType;
        intFrom = inputFrom;
        intTo = inputTo;
        isChangeable = true;
        hasTypeLimit = 1;
        realArray = new float[inputTo - inputFrom];
        intArray = new int[inputTo - inputFrom];
        boolArray = new boolean[inputTo - inputFrom];
        stringArray = new String[inputTo - inputFrom];
        charArray = new char[inputTo - inputFrom];
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

    public void setArray(int inputIndex, Node inputNode, variableType inputVariableType) {
        switch (inputVariableType) {
            case INTEGER:
                IntegerNode newIntegerNode = (IntegerNode) inputNode;
                int newInteger = newIntegerNode.getValue();
                intArray[inputIndex] = newInteger;
                break;
            case REAL:
                RealNode newRealNode = (RealNode) inputNode;
                float newReal = newRealNode.getValue();
                realArray[inputIndex] = newReal;
                break;
            case STRING:
                StringNode newStringNode = (StringNode) inputNode;
                String newString = newStringNode.getValue();
                stringArray[inputIndex] = newString;
                break;
            case CHARACTER:
                CharacterNode newCharacterNode = (CharacterNode) inputNode;
                char newCharacter = newCharacterNode.getValue();
                charArray[inputIndex] = newCharacter;
                break;
            case BOOLEAN:
                BooleanNode newBooleanNode = (BooleanNode) inputNode;
                boolean newBoolean = newBooleanNode.getValue();
                boolArray[inputIndex] = newBoolean;
                break;
            default :
                System.out.println("ERROR: Incorrect data type given in argument array.");
                System.exit(0);
        }
    }

    public Node getArrayValueAtIndex(int inputIndex, variableType inputType) {
        switch (inputType) {
            case REAL :
                float realValue = realArray[inputIndex];
                RealNode arrayRealNode = new RealNode(realValue);
                return arrayRealNode;
            case INTEGER :
                int intValue = intArray[inputIndex];
                IntegerNode arrayIntNode = new IntegerNode(intValue);
                return arrayIntNode;
            case STRING :
                String stringValue = stringArray[inputIndex];
                StringNode arrayStringNode = new StringNode(stringValue);
                return arrayStringNode;
            case CHARACTER :
                Character characterValue = charArray[inputIndex];
                CharacterNode arrayCharacterNode = new CharacterNode(characterValue);
                return arrayCharacterNode;
            case BOOLEAN :
                boolean booleanValue = boolArray[inputIndex];
                BooleanNode arrayBooleanNode = new BooleanNode(booleanValue);
                return arrayBooleanNode;
            default :
                return null;
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

    public int getHasTypeLimit() {
        return hasTypeLimit;
    }

    public void setRange(int inputStart, int inputEnd) {
        intFrom = inputStart;
        intTo = inputEnd;
        realArray = new float[intTo - intFrom];
        intArray = new int[intTo - intFrom];
        boolArray = new boolean[intTo - intFrom];
        stringArray = new String[intTo - intFrom];
        charArray = new char[intTo - intFrom];
    }
    
    public String ToString() {
        return "VariableNode(" + name + "," + type + ")";
    }
    
    
}
