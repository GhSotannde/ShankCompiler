public class MathOpNode extends StatementNode {

    public enum operationType { ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD }
    private operationType type;
    private Node left;
    private Node right;
    private float value;
    private String stringValue = null;
    
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

    public void setValue(int inputValue) {
        value = (float) inputValue;
    }

    public void setValue(float inputValue) {
        value = inputValue;
    }

    public void setStringValue(String inputString) {
        stringValue = inputString;
    }

    public String getStringValue() {
        return stringValue;
    }

    public float getValue() {
        return value;
    }

}
