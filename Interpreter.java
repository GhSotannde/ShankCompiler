import java.util.HashMap;

public class Interpreter {

    private HashMap<String, InterpreterDataType> localVariableMap = new HashMap<String, InterpreterDataType>();

    private Interpreter() {

    }

    public void addToVariableMap(String inputName, InterpreterDataType inputDataType) {
        localVariableMap.put(inputName, inputDataType);
    }

    private FunctionNode interpretFunction() {
        return null;
    }

}
