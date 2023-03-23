public class ParameterNode extends StatementNode {
    
    private VariableReferenceNode varIdentifier = null;
    private Node expression = null;

    public ParameterNode(VariableReferenceNode inputVarIdentifier) {
        varIdentifier = inputVarIdentifier;
        expression = null;
    }

    public ParameterNode(Node inputExpression) {
        expression = inputExpression;
        varIdentifier = null;
    }

    public String ToString() {
        String str = "\nParameterNode(\n";
        if (varIdentifier != null)
            str += " Var Identifier: " +varIdentifier.ToString();
        if (expression != null) {
            str += " Expression: " + expression.ToString();
        }
        str += "\n)";
        return str;
    }
}
