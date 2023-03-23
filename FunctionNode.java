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

    private boolean isVariadic() {
        boolean result = (name == "Read" || name == "Write") ? true : false;
        return result;
    }

    private String ToStringParameterArray() {
        String str = "Parameters:\n";
        for (VariableNode node : parameterArray) {
            str += "    ";
            if (node.getChangeable() == true)
                str += "var ";
            str += node.getName() + ":" + node.getType() + "\n";
        }
        str += "\n";
        return str;
    }

    private String ToStringVariableArray() {
        String str = "Variables:\n";
        for (VariableNode node : variableArray) {
            str += "    " + node.getName() + ":" + node.getType();
            if (node.getValue() != null) {
                str += " = " + node.getValue().ToString();
            }
            if (node.getHasTypeLimit() == 1) { //If == 1, node has type limit and needs to print accordingly
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

    private String ToStringStatementArray() {
        String str = "Function Statements:\n\n";
        for (StatementNode node : statementArray) {
            str += node.ToString() + "\n";
        }
        return str;
    }

    public String ToString() {
        String str = "Function Name: " + name + "\n\n";
        if (parameterArray != null)
            str += ToStringParameterArray();
        if (variableArray != null)
            str += ToStringVariableArray();
        if (statementArray != null)
            str += ToStringStatementArray();
        str += "\n";
        return str;
    }

    public String getName() {
        return name;
    }

    public ArrayList<VariableNode> getVariableArray() {
        return variableArray;
    }

    public ArrayList<StatementNode> getStatementArray() {
        return statementArray;
    }


    
}
