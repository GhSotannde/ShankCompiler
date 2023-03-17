public abstract class InterpreterDataType {
    public abstract boolean isChangeable();
    
    public abstract String ToString();

    public abstract void FromString(String input);
}
