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

    public String ToString() {
        if (condition == null) {
            String str = "\nElseNode(\nStatements:\n";
            if (statements != null) 
            for (int i = 0; i < statements.size() ; i++) {
                str += statements.get(i).ToString();
            }
            str += "\n)";
            return str;
        }
        String str = "\nIfNode(\nCondition: ";
        str += condition.ToString() + "\nStatements:\n";
        
        for (int i = 0; i < statements.size() ; i++) {
            str += statements.get(i).ToString();
        }
        str += "\n)";
        str += nextIf.ToString();
        
        return str;
    } 
}
