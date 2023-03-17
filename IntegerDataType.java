public class IntegerDataType extends InterpreterDataType {
    
    private int data;
    private boolean isChangeable;

    public IntegerDataType(int inputData) {
        data = inputData;
    }

    public void FromString(String input) {
        
    }

    public int getData() {
        return data;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(int inputData) {
        if (isChangeable == true)
            data = inputData;
    }

    public String ToString() {
        String str = "" + data;
        return str;
    }
}
