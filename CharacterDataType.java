public class CharacterDataType extends InterpreterDataType {
    
    private char data;
    private boolean isChangeable;

    public CharacterDataType(char inputData, boolean inputIsChangeable) {
        data = inputData;
        isChangeable = inputIsChangeable;
    }

    public void FromString(String input) {
        
    }

    public char getData() {
        return data;
    }

    public boolean isChangeable() {
        return isChangeable;
    }

    public void setData(char inputData) {
        if (isChangeable == true)
            data = inputData;
    }

    public String ToString() {
        return "CharacterDataType(" + data + ")";
    }
}