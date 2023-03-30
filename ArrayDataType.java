import java.util.ArrayList;

public class ArrayDataType extends InterpreterDataType {
    
    private ArrayList<InterpreterDataType> data = new ArrayList<InterpreterDataType>();
    private boolean isChangeable;
    private int startIndex = 0;
    private int endIndex = 0;
    public enum arrayDataType { REAL, INTEGER, CHARACTER, STRING, BOOLEAN }
    private arrayDataType type;


    public ArrayDataType(arrayDataType inputArrayDataType, int inputStartIndex, int inputEndIndex, boolean inputIsChangeable) {
        type = inputArrayDataType;
        isChangeable = inputIsChangeable;
        startIndex = inputStartIndex;
        endIndex = inputEndIndex;
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
