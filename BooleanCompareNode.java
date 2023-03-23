public class BooleanCompareNode extends StatementNode {
    public enum comparisonType { LESSTHAN, GREATERTHAN, LESSTHANOREQUALTO, GREATERTHANOREQUALTO, EQUAL, NOTEQUAL }
    private comparisonType type;

    private Node left;
    private Node right;
    private Integer value;

    public BooleanCompareNode(comparisonType inputType, Node inputLeft, Node inputRight, Integer inputValue) {
    type = inputType;
    left = inputLeft;
    right = inputRight;
    value = inputValue; //Can be 0 or 1
    }

    public String ToString() {
        return "BooleanCompareNode(\n  Type: " + type + "\n  Value: " + value + "\n  Left: " + left.ToString() + "\n  Right: " + right.ToString() + "\n)";
    }

    public Integer getValue() {
        return value;
    }
}
