public class RealDataType extends InterpreterDataType {
    
    private float data;
    private boolean isChangeable;

    public RealDataType(float inputData) {
        data = inputData;
    }

    public void FromString(String input) {
        
    }

    public float getData() {
        return data;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(float inputData) {
        if (isChangeable == true)
            data = inputData;
    }

    public String ToString() {
        return "RealDataType(" + data + ")";
    }
}