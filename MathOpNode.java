public class MathOpNode extends StatementNode {

    public enum operationType { ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD }
    private operationType type;
    private Node left;
    private Node right;

    public MathOpNode() {
    }

    public MathOpNode(operationType inputType, Node inputLeft, Node inputRight) {
        type = inputType;
        left = inputLeft;
        right = inputRight;
    }

    public String ToString() {
        String str = "";
        if (type != null) {
            str = "MathOpNode(" + type;
            if (left != null && left instanceof Node) {
                str += ", " + left.ToString();
            }
            else {
                str += ", NULL";
            }
            if (right != null && right instanceof Node) {
                str += ", " + right.ToString();
            }
            else {
                str += ", NULL";
            }
            str += ")";
            return str;
        }
        return "";
    }

    public Node getLeftChild() {
        return left;
    }

    public Node getRightChild() {
        return right;
    }

    public operationType getType() {
        return type;
    }

    public VariableNode.variableType getDataType() {
        VariableNode.variableType leftSideType = null;
        VariableNode.variableType rightSideType = null;
        if (left instanceof VariableReferenceNode || right instanceof VariableReferenceNode) {
            if (left instanceof VariableReferenceNode && right instanceof VariableReferenceNode) {
                VariableReferenceNode leftVariableReference = (VariableReferenceNode) left;
                VariableReferenceNode rightVariableReference = (VariableReferenceNode) right;
                leftSideType = leftVariableReference.getType();
                rightSideType = rightVariableReference.getType();
            }
            else if (left instanceof VariableReferenceNode) {
                VariableReferenceNode leftVariableReferenceNode = (VariableReferenceNode) left;
                leftSideType = leftVariableReferenceNode.getType();
            }
            else if (right instanceof VariableReferenceNode) {
                VariableReferenceNode rightVariableReferenceNode = (VariableReferenceNode) right;
                rightSideType = rightVariableReferenceNode.getType();
            }
        }
        if (left instanceof MathOpNode) {
            MathOpNode leftMathOpNode = (MathOpNode) left;
            leftSideType = leftMathOpNode.getDataType();
            if (right instanceof MathOpNode) {
                MathOpNode rightMathOpNode = (MathOpNode) right;
                rightSideType = rightMathOpNode.getDataType();
            }
            else if (right instanceof IntegerNode) {
                rightSideType = VariableNode.variableType.INTEGER;
            }
            else if (right instanceof RealNode) {
                rightSideType = VariableNode.variableType.REAL;
            }
            else if (right instanceof StringNode) {
                rightSideType = VariableNode.variableType.STRING;
            }
        }
        else if (right instanceof MathOpNode) {
            MathOpNode rightMathOpNode = (MathOpNode) right;
            rightSideType = rightMathOpNode.getDataType();
            if (left instanceof MathOpNode) {
                MathOpNode leftMathOpNode = (MathOpNode) left;
                leftSideType = leftMathOpNode.getDataType();
            }
            else if (left instanceof IntegerNode) {
                leftSideType = VariableNode.variableType.INTEGER;
            }
            else if (left instanceof RealNode) {
                leftSideType = VariableNode.variableType.REAL;
            }
            else if (left instanceof StringNode) {
                leftSideType = VariableNode.variableType.STRING;
            }
        }
        if (left instanceof IntegerNode) {
            leftSideType = VariableNode.variableType.INTEGER;
        }
        if (right instanceof IntegerNode) {
            rightSideType = VariableNode.variableType.INTEGER;
        }
        if (left instanceof RealNode) {
            leftSideType = VariableNode.variableType.REAL;
        }
        if (right instanceof RealNode) {
            rightSideType = VariableNode.variableType.REAL;
        }
        if (left instanceof StringNode) {
            leftSideType = VariableNode.variableType.STRING;
        }
        if (right instanceof StringNode) {
            rightSideType = VariableNode.variableType.STRING;
        }
        if (leftSideType == rightSideType) {
            return leftSideType;
        }
        else {
            System.out.println("ERROR: Multiple data types used in math operation.");
            System.exit(3);
        }
        return null;
    }
}
