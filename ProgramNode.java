import java.util.HashMap;

public class ProgramNode extends Node {
    private HashMap<String, FunctionNode> functionMap = new HashMap<String, FunctionNode>();

    public ProgramNode() {
    }

    public void addToFunctionMap(FunctionNode inputFunctionNode) {
        functionMap.put(inputFunctionNode.getName(), inputFunctionNode);
    }

    public String ToString() {
        String str = "";
        for (String key: functionMap.keySet()){
            str += functionMap.get(key).ToString();
        }
        return str;
    }
}