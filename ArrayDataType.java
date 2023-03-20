import java.util.ArrayList;

public class ArrayDataType extends InterpreterDataType {
    
    private ArrayList<InterpreterDataType> data = new ArrayList<InterpreterDataType>();
    private boolean isChangeable;

    public ArrayDataType(ArrayList<InterpreterDataType> inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public void FromString(String input) {
        
    }

    public ArrayList<InterpreterDataType> getData() {
        return data;
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
