import java.util.ArrayList;

public class WhileNode extends StatementNode {

    private BooleanCompareNode condition;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();

    public WhileNode(BooleanCompareNode inputCondition, ArrayList<StatementNode> inputStatementArray) {
        condition = inputCondition;
        statements = inputStatementArray;
    }

    public String ToString() {
        String str = "WhileNode(Condition: ";
        str += condition.ToString() + " Statements: ";
        
        for (int i = 0; i < statements.size() ; i++) {
            str += ", " + statements.get(i).ToString();
        }
        str += ")";
        return str;
    } 

}