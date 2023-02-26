import java.util.ArrayList;

public class FunctionNode extends Node {
    private String name;
    private ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    private ArrayList<VariableNode> variableArray = new ArrayList<VariableNode>();
    private ArrayList<StatementNode> statementArray = new ArrayList<StatementNode>();

    public FunctionNode(String inputName, ArrayList<VariableNode> inputParameterArray,
    ArrayList<VariableNode> inputVariableArray, ArrayList<StatementNode> inputStatementArray) {
        name = inputName;
        parameterArray = inputParameterArray;
        variableArray = inputVariableArray;
        statementArray = inputStatementArray;
    } 

    public String ToString() {
        return "Function " + name + "\n" + printParameterArray() + printVariableArray();
    }

    public String getName() {
        return name;
    }

    private String printParameterArray() {
        String str = "Parameters: ";
        for (VariableNode node : parameterArray) {
            str += node.getName() + ":" + node.getType() + ", ";
        }
        str += "\n";
        return str;
    }

    private String printVariableArray() {
        String str = "Variables: ";
        for (VariableNode node : variableArray) {
            str += node.getName() + ":" + node.getType() + ", ";
        }
        str += "\n";
        return str;
    }
}
