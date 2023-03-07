public class StatementNode extends Node {

    private AssignmentNode statement;

    public StatementNode() {
        
    }

    public StatementNode(AssignmentNode inputStatement) {
        statement = inputStatement;
    }
    
    public String ToString() {
        return "StatementNode(" + statement.ToString() + ")";
    }
}
