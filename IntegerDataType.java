public class IntegerDataType extends InterpreterDataType {
    
    private int data;
    private boolean isChangeable;
    private boolean hasTypeLimit;
    private int typeLimitFrom;
    private int typeLimitTo;

    public IntegerDataType(int inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public IntegerDataType(int inputData, boolean inputIsChangeable, int inputTypeLimitFrom, int inputTypeLimitTo) {
        data = inputData;
        isChangeable = inputIsChangeable;
        typeLimitFrom = inputTypeLimitFrom;
        typeLimitTo = inputTypeLimitTo;
        hasTypeLimit = true;
    }


    public void FromString(String input) {
        
    }

    public int getData() {
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

    public void setData(int inputData) {
        if (isChangeable == true) {
            if (hasTypeLimit == true) {
                if (inputData >= typeLimitFrom && inputData <= typeLimitTo)
                    data = inputData;
                else {
                    System.out.println("ERROR: " + inputData + " is outside of range for integer variable with type limit.");
                    System.exit(0);
                }
            }
            else
                data = inputData;
        }
    }

    public String ToString() {
        String str = "" + data;
        return str;
    }
}
