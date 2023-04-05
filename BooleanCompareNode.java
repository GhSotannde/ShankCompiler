public class BooleanCompareNode extends StatementNode {
    public enum comparisonType { LESSTHAN, GREATERTHAN, LESSTHANOREQUALTO, GREATERTHANOREQUALTO, EQUAL, NOTEQUAL }
    private comparisonType type;
    private Node left;
    private Node right;

    public BooleanCompareNode(comparisonType inputType, Node inputLeft, Node inputRight) {
    type = inputType;
    left = inputLeft;
    right = inputRight;
    }

    public Node getLeftChild() {
        return left;
    }

    public Node getRightChild() {
        return right;
    }

    public comparisonType getType() {
        return type;
    }

    public String ToString() {
        return "BooleanCompareNode(\n  Type: " + type + "\n  Left: " + left.ToString() + "\n  Right: " + right.ToString() + "\n)";
    }
}
