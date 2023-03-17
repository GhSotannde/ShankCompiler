public class BooleanDataType extends InterpreterDataType {
    
    private boolean data;
    private boolean isChangeable;

    public BooleanDataType(boolean inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public void FromString(String input) {
        
    }

    public boolean getData() {
        return data;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(Boolean inputData) {
        if (isChangeable == true)
            data = inputData;
    }

    public String ToString() {
        return "BooleanDataType(" + data + ")";
    }
}