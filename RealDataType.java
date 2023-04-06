public class RealDataType extends InterpreterDataType {
    
    private float data;
    private boolean isChangeable;
    private boolean hasTypeLimit;
    private float typeLimitFrom;
    private float typeLimitTo;

    public RealDataType(float inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public RealDataType(float inputData, boolean inputIsChangeable, float inputTypeLimitFrom, float inputTypeLimitTo) {
        data = inputData;
        isChangeable = inputIsChangeable;
        typeLimitFrom = inputTypeLimitFrom;
        typeLimitTo = inputTypeLimitTo;
        hasTypeLimit = true;
    }

    public void FromString(String input) {
        
    }

    public float getData() {
        return data;
    }

    public float getTypeLimitFrom() {
        return typeLimitFrom;
    }

    public float getTypeLimitTo() {
        return typeLimitTo;
    }

    public boolean hasTypeLimit() {
        return hasTypeLimit;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(float inputData) {
        if (isChangeable == true) {
            if (hasTypeLimit == true) {
                if (inputData >= typeLimitFrom && inputData <= typeLimitTo)
                    data = inputData;
                else {
                    System.out.println("ERROR: " + inputData + " is outside of range for real variable with type limit.");
                    System.exit(0);
                }
            }
            else
                data = inputData;
        }
    }

    public void setTypeLimit(boolean inputHasTypeLimit) {
        hasTypeLimit = inputHasTypeLimit;
    }

    public String ToString() {
        return "RealDataType(" + data + ")";
    }
}