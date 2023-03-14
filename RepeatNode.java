import java.util.ArrayList;

public class RepeatNode extends StatementNode {

    private BooleanCompareNode condition;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();

    public RepeatNode(BooleanCompareNode inputCondition, ArrayList<StatementNode> inputStatementArray) {
        condition = inputCondition;
        statements = inputStatementArray;
    }

    public String ToString() {
        String str = "RepeatNode(Condition: ";
        str += condition.ToString() + " Statements: ";
        
        for (int i = 0; i < statements.size() ; i++) {
            str += ", " + statements.get(i).ToString();
        }
        str += ")";
        return str;
    } 

}