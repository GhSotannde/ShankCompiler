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
        String str = "\nForNode(\n";
        str += " Variable: " + integerVariable.ToString() + "\n From: " + from.ToString() + "\n To: " + to.ToString() +  "\n Statements:\n";
        for (int i = 0; i < statements.size() ; i++) {
            str += statements.get(i).ToString();
        }
        str += "\n)";
        return str;
    } 
}