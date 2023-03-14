import java.util.ArrayList;

public class WhileNode extends StatementNode {

    private BooleanCompareNode condition;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();

    public WhileNode(BooleanCompareNode inputCondition, ArrayList<StatementNode> inputStatementArray) {
        condition = inputCondition;
        statements = inputStatementArray;
    }

    public String ToString() {
        String str = "\nWhileNode(\nCondition: ";
        str += condition.ToString() + "\nStatements:\n";
        
        for (int i = 0; i < statements.size() ; i++) {
            str += statements.get(i).ToString();
        }
        str += "\n)";
        return str;
    } 
}