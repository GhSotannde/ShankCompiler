public class StringDataType extends InterpreterDataType {
    
    private String data;
    private boolean isChangeable;

    public StringDataType(String inputData) {
        data = inputData;
    }

    public void FromString(String input) {
        
    }

    public String getData() {
        return data;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(String inputData) {
        if (isChangeable == true)
            data = inputData;
    }

    public String ToString() {
        return "StringDataType(" + data + ")";
    }
}