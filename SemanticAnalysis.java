import java.util.ArrayList;

public class SemanticAnalysis {

    public SemanticAnalysis() {
    }

    public void CheckAssignment(ProgramNode inputProgramNode) {
        ArrayList<AssignmentNode> assignmentNodeArray = inputProgramNode.getAssignmentNodeArray();
        for (AssignmentNode assignmentNode : assignmentNodeArray) {
            VariableNode.variableType leftType;
            VariableNode.variableType rightType;
            String variableName = assignmentNode.getTarget().getName();
            leftType = getLeftSideType(assignmentNode);
            rightType = getRightSideType(assignmentNode);
            if (leftType == null) {
                System.out.println("Variable '" + variableName + "' has an unrecognizable data type.");
                System.exit(0);
            }
            if (rightType == null) {
                System.out.println("Data assigned to variable: '" + variableName +  "' has an unrecognizable data type");
                System.exit(1);
            }
            if (leftType != rightType) {
                System.out.println("Data assigned to variable: '" + variableName +  "' does not match variable's data type");
                System.exit(2);
            }
        }
    }

    private VariableNode.variableType getLeftSideType(AssignmentNode inputAssignmentNode) {
        if (inputAssignmentNode.getTarget().getReferencedVariable().getChangeable() == false) { //Checks for constants
            String variableName = inputAssignmentNode.getTarget().getName();
            System.out.println("Variable '" + variableName +"' is a constant and cannot be changed.");
            System.exit(3);
        }
        return inputAssignmentNode.getTarget().getType();
    }

    private VariableNode.variableType getRightSideType(AssignmentNode inputAssignmentNode) {
        Node value = inputAssignmentNode.getValue();
        if (value instanceof MathOpNode) {
            MathOpNode mathOpNodeValue = (MathOpNode) value;
            return mathOpNodeValue.getDataType();
        }
        else if (value instanceof IntegerNode) {
            return VariableNode.variableType.INTEGER;
        }
        else if (value instanceof RealNode) {
            return VariableNode.variableType.REAL;
        }
        else if (value instanceof BooleanNode) {
            return VariableNode.variableType.BOOLEAN;
        }
        else if (value instanceof StringNode) {
            return VariableNode.variableType.STRING;
        }
        else if (value instanceof CharacterNode) {
            return VariableNode.variableType.CHARACTER;
        }
        return null;
    }
}
