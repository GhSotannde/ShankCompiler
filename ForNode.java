import java.util.ArrayList;

public class ForNode extends StatementNode {

    private Node from;
    private Node to;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
    private VariableReferenceNode integerVariable;

    public ForNode(VariableReferenceNode inputIntegerVariable, Node inputFrom, Node inputTo, ArrayList<StatementNode> inputStatementArray) {
        from = inputFrom;
        to = inputTo;
        statements = inputStatementArray;
        integerVariable = inputIntegerVariable;
    }

    public String ToString() {
        String str = "ForNode(\n";
        str += "Variable: " + integerVariable.ToString() + "\nFrom: " + from.ToString() + "\nTo: " + to.ToString() +  "\nStatements:";
        for (int i = 0; i < statements.size() ; i++) {
            str += statements.get(i).ToString();
        }
        str += "\n)";
        return str;
    } 
}