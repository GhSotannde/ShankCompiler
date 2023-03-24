import java.util.ArrayList;

public class IfNode extends StatementNode {

    private BooleanCompareNode condition = null;
    private ArrayList<StatementNode> statements = new ArrayList<StatementNode>();
    private IfNode nextIf = null;

    public IfNode(BooleanCompareNode inputCondition, ArrayList<StatementNode> inputStatementArray, IfNode inputNextIf) {
        condition = inputCondition;
        statements = inputStatementArray;
        nextIf = inputNextIf;
    }

    public IfNode(ArrayList<StatementNode> inputStatementArray) {
        statements = inputStatementArray;
    }

    public BooleanCompareNode getCondition() {
        if (condition == null)
            return null;
        return condition;
    }

    public ArrayList<StatementNode> getStatements() {
        return statements;
    }

    public IfNode getNextIf() {
        return nextIf;
    }

    public String ToString() {
        if (condition == null) {
            String str = "\nElseNode(\n Statements:\n";
            if (statements != null) 
            for (int i = 0; i < statements.size() ; i++) {
                str += statements.get(i).ToString();
            }
            str += "\n)";
            return str;
        }
        String str = "\nIfNode(\n Condition: ";
        str += condition.ToString() + "\n Statements:\n";
        for (int i = 0; i < statements.size() ; i++) {
            str += statements.get(i).ToString();
        }
        str += "\n)";
        if (nextIf != null) 
            str += nextIf.ToString();
        return str;
    } 
}
