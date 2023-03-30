import java.util.HashMap;

public class ProgramNode extends Node {
    private HashMap<String, FunctionNode> functionMap = new HashMap<String, FunctionNode>();

    private BuiltInWrite builtInWrite = new BuiltInWrite();
    private BuiltInLeft builtInLeft = new BuiltInLeft();
    private BuiltInRight builtInRight = new BuiltInRight();
    private BuiltInSubstring builtInSubstring = new BuiltInSubstring();
    private BuiltInSquareRoot builtInSquareRoot = new BuiltInSquareRoot();
    private BuiltInGetRandom builtInGetRandom = new BuiltInGetRandom();
    private BuiltInIntegerToReal builtInIntegerToReal = new BuiltInIntegerToReal();
    private BuiltInRealToInteger builtInRealToInteger = new BuiltInRealToInteger();
    private BuiltInStart builtInStart = new BuiltInStart();
    private BuiltInEnd builtInEnd = new BuiltInEnd();
    private BuiltInRead builtInRead = new BuiltInRead();

    public ProgramNode() {
        addToFunctionMap(builtInWrite);
        addToFunctionMap(builtInLeft);
        addToFunctionMap(builtInRight);
        addToFunctionMap(builtInSubstring);
        addToFunctionMap(builtInSquareRoot);
        addToFunctionMap(builtInGetRandom);
        addToFunctionMap(builtInIntegerToReal);
        addToFunctionMap(builtInRealToInteger);
        addToFunctionMap(builtInStart);
        addToFunctionMap(builtInEnd);
        addToFunctionMap(builtInRead);
    }

    public void addToFunctionMap(FunctionNode inputFunctionNode) {
        functionMap.put(inputFunctionNode.getName(), inputFunctionNode);
    }

    public String ToString() {
        String str = "";
        for (String key: functionMap.keySet()){
            str += functionMap.get(key).ToString() + "\n\n";
        }
        return str;
    }

    public boolean isAFunction(String inputString) {
        return functionMap.containsKey(inputString);
    }

    public HashMap<String, FunctionNode> getFunctionMap() {
        return functionMap;
    }
}