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
        return "Function Name: " + name + "\n\n" + printParameterArray() + printVariableArray() + printStatementArray() + "\n";
    }

    public String getName() {
        return name;
    }

    private String printParameterArray() {
        String str = "Parameters:\n";
        for (VariableNode node : parameterArray) {
            str += "    ";
            if (node.getChangeable() == 1)
                str += "var ";
            str += node.getName() + ":" + node.getType() + "\n";
        }
        str += "\n";
        return str;
    }

    private String printVariableArray() {
        String str = "Variables:\n";
        for (VariableNode node : variableArray) {
            str += "    " + node.getName() + ":" + node.getType();
            if (node.getHasTypeLimit() == 1) {
                if (node.getType() == VariableNode.variableType.REAL) {
                    str += " From " + node.getRealFrom() + " To " + node.getRealTo();
                }
                else {
                    str += " From " + node.getIntFrom() + " To " + node.getIntTo();
                }
            }
            str += "\n";
        }
        str += "\n";
        return str;
    }

    private String printStatementArray() {
        String str = "Statements:\n\n";
        for (StatementNode node : statementArray) {
            str += node.ToString();
        }
        return str;
    }

    
}
