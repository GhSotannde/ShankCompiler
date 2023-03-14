public class StatementNode extends Node {

    private Node statement;

    public StatementNode() {
        
    }

    public StatementNode(Node inputStatement) {
        statement = inputStatement;
    }
    
    public String ToString() {
        return statement.ToString();
    }
}
