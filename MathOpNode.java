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
}
