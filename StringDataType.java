public class StringDataType extends InterpreterDataType {
    
    private String data;
    private boolean isChangeable;
    private boolean hasTypeLimit;
    private int typeLimitFrom;
    private int typeLimitTo;

    public StringDataType(String inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public StringDataType(String inputData, boolean inputIsChangeable, int inputTypeLimitFrom, int inputTypeLimitTo) {
        data = inputData;
        isChangeable = inputIsChangeable;
        typeLimitFrom = inputTypeLimitFrom;
        typeLimitTo = inputTypeLimitTo;
        hasTypeLimit = true;
    }

    public void FromString(String input) {
        
    }

    public String getData() {
        return data;
    }

    public int getTypeLimitFrom() {
        return typeLimitFrom;
    }

    public int getTypeLimitTo() {
        return typeLimitTo;
    }

    public boolean hasTypeLimit() {
        return hasTypeLimit;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(String inputData) {
        if (isChangeable == true) {
            if (hasTypeLimit == true) {
                if (inputData.length() >= typeLimitFrom && inputData.length() <= typeLimitTo)
                    data = inputData;
                else {
                    System.out.println("ERROR: Length of string " + inputData + " is outside of range for string variable with type limit.");
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
        return "StringDataType(" + data + ")";
    }
}