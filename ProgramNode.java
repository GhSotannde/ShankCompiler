import java.util.HashMap;

public class ProgramNode extends Node {
    private HashMap<String, FunctionNode> functionMap = new HashMap<String, FunctionNode>();

    public String ToString() {
        return "ProgramNode(" + ")";
    }
}
