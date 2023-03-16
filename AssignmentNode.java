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
        String str = "\nAssignmentNode(\n  Target: " + targetOfAssignment.ToString();
        if (assignmentValue instanceof MathOpNode) {
            MathOpNode expressionValue = (MathOpNode) assignmentValue;
            str += "\n  Value: " + expressionValue.ToString();
        }
        else if (assignmentValue instanceof IntegerNode) {
            IntegerNode expressionValue = (IntegerNode) assignmentValue;
            str += "\n  Value: " + expressionValue.ToString();
        }
        else if (assignmentValue instanceof RealNode) {
            RealNode expressionValue = (RealNode) assignmentValue;
            str += "\n  Value:" + expressionValue.ToString();
        }
        else if (assignmentValue instanceof BooleanCompareNode) {
            BooleanCompareNode expressionValue = (BooleanCompareNode) assignmentValue;
            str += "\n  Value:" + expressionValue.ToString();
        }
        else if (assignmentValue instanceof VariableReferenceNode) {
            VariableReferenceNode expressionValue = (VariableReferenceNode) assignmentValue;
            str += "\n  Value:" + expressionValue.ToString();
        }
        str += "\n)";
        return str;
    }
}
