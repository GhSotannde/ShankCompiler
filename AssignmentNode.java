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
        String str = "AssignmentNode(Target: " + targetOfAssignment.ToString();
        if (assignmentValue instanceof MathOpNode) {
            MathOpNode expressionValue = (MathOpNode) assignmentValue;
            str += " | Assignment Value: " + expressionValue.ToString();
        }
        else if (assignmentValue instanceof IntegerNode) {
            IntegerNode expressionValue = (IntegerNode) assignmentValue;
            str += " | Assignment Value: " + expressionValue.ToString();
        }
        else if (assignmentValue instanceof RealNode) {
            RealNode expressionValue = (RealNode) assignmentValue;
            str += " | Assignment Value:" + expressionValue.ToString();
        }
        else if (assignmentValue instanceof BooleanCompareNode) {
            BooleanCompareNode expressionValue = (BooleanCompareNode) assignmentValue;
            str += " | Assignment Value:" + expressionValue.ToString();
        }
        else if (assignmentValue instanceof VariableReferenceNode) {
            VariableReferenceNode expressionValue = (VariableReferenceNode) assignmentValue;
            str += " | Assignment Value:" + expressionValue.ToString();
        }
        str += ")\n";
        return str;
    }
}
