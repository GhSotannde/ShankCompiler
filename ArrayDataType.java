import java.util.ArrayList;

public class ArrayDataType extends InterpreterDataType {
    
    public enum arrayDataType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN }
    private arrayDataType type;
    private ArrayList<InterpreterDataType> data = new ArrayList<InterpreterDataType>();
    private boolean isChangeable;
    private int startIndex = 0;
    private int endIndex = 0;
    
    public ArrayDataType(arrayDataType inputArrayDataType, int inputStartIndex, int inputEndIndex, boolean inputIsChangeable) {
        type = inputArrayDataType;
        isChangeable = inputIsChangeable;
        startIndex = inputStartIndex;
        endIndex = inputEndIndex;
        switch (type) {
            case REAL: 
                for (int i = 0; i < endIndex - startIndex; i++) {
                    RealDataType realData = new RealDataType(0, true);
                    data.add(realData);
                }
                break;
            case INTEGER: 
                for (int i = 0; i < endIndex - startIndex; i++) {
                    IntegerDataType integerData = new IntegerDataType(0, true);
                    data.add(integerData);
                }
                break;
            case BOOLEAN: 
                for (int i = 0; i < endIndex - startIndex; i++) {
                    BooleanDataType booleanData = new BooleanDataType(false, true);
                    data.add(booleanData);
                }
                break;
            case STRING: 
                for (int i = 0; i < endIndex - startIndex; i++) {
                    StringDataType stringData = new StringDataType("", true);
                    data.add(stringData);
                }
                break;
            case CHARACTER: 
                for (int i = 0; i < endIndex - startIndex; i++) {
                    CharacterDataType characterData = new CharacterDataType(' ', true);
                    data.add(characterData);
                }
                break;
            default :
                System.out.println("ERROR: Data type not provided for array.");
                System.exit(0);
        }
    }

    public ArrayDataType(ArrayList<InterpreterDataType> inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public void FromString(String input) {
        
    }

    public ArrayList<InterpreterDataType> getData() {
        return data;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public arrayDataType getArrayType() {
        return type;
    }

    public InterpreterDataType getDataAtIndex(int inputIndex) {
        return data.get(inputIndex);
    }

    public void setIndex(int inputIndex, InterpreterDataType inputData) {
        data.set(inputIndex, inputData);
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public String ToString() {
        String str = "ArrayDataType(";
        for (int i = 0; i < data.size(); i++) {
            str += data.get(i) + ", ";
        }
        str += ")";
        return str;
    }
}
