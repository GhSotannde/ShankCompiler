import java.util.ArrayList;

public class ForNode extends StatementNode {

    private Node from;
    private Node to;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();

    public ForNode(Node inputFrom, Node inputTo, ArrayList<StatementNode> inputStatementArray) {
        from = inputFrom;
        to = inputTo;
        statements = inputStatementArray;
    }

    public String ToString() {
        String str = "ForNode( ";
        str += "From: " + from.ToString() + ", To: " + to.ToString() +  ", Statements: ";
        for (int i = 0; i < statements.size() ; i++) {
            str += ", " + statements.get(i).ToString();
        }
        str += ")";
        return str;
    } 
}