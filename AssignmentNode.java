public class AssignmentNode extends StatementNode {

    private VariableReferenceNode targetOfAssignment;
    private Node assignmentValue;
    
    public AssignmentNode(VariableReferenceNode inputTargetOfAssignment, Node inputAssignmentValue) {
        targetOfAssignment = inputTargetOfAssignment;
        assignmentValue = inputAssignmentValue;
    }

    public VariableReferenceNode getTarget() {
        return targetOfAssignment;
    }

    public Node getValue() {
        return assignmentValue;
    }

    public String ToString() {
        String str = "AssignmentNode( Target: " + targetOfAssignment.ToString();
        if (assignmentValue instanceof MathOpNode) {
            MathOpNode mathOpNodeExpression = (MathOpNode) assignmentValue;
            str += ", Expression: " + mathOpNodeExpression.ToString();
        }
        str += ")";
        return str;
    }
}
