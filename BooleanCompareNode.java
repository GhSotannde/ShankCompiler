public class BooleanCompareNode extends Node {
    public enum comparisonType { LESSTHAN, GREATERTHAN, LESSTHANOREQUALTO, GREATERTHANOREQUALTO, EQUAL, NOTEQUAL }
    private comparisonType type;

    private Node left;
    private Node right;
    private Integer value;

    public BooleanCompareNode(comparisonType inputType, Node inputLeft, Node inputRight, Integer inputValue) {
    type = inputType;
    left = inputLeft;
    right = inputRight;
    value = inputValue;
    }

    public String ToString() {
        return "BooleanCompareNode()";
    }

    public Integer getValue() {
        return value;
    }
}
