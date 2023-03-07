public class VariableReferenceNode extends Node{
    
    private String name;
    private float value;
    private Node arrayIndexExpression = null;
    private Integer arrayIndex;

    public VariableReferenceNode(String inputName) {
        name = inputName;
    }

    public VariableReferenceNode(String inputName, Node inputArrayIndexExpression) {
        name = inputName;
        arrayIndexExpression = inputArrayIndexExpression;
    }

    public void setValue(float inputValue) {
        value = inputValue;
    }

    public float getValue() {
        return value;
    }

    public String ToString() {
        String str = "VariableReferenceNode(" + name;
        if (arrayIndexExpression instanceof VariableReferenceNode) {
            VariableReferenceNode innerExpression = (VariableReferenceNode) arrayIndexExpression;
            str += ", " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof IntegerNode) {
            IntegerNode innerExpression = (IntegerNode) arrayIndexExpression;
            str += ", " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof RealNode) {
            RealNode innerExpression = (RealNode) arrayIndexExpression;
            str += ", " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof MathOpNode) {
            MathOpNode innerExpression = (MathOpNode) arrayIndexExpression;
            str += ", " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof BooleanCompareNode) {
            BooleanCompareNode innerExpression = (BooleanCompareNode) arrayIndexExpression;
            str += ", " + innerExpression.ToString();
        }
        str += ")";
        return str;
    }
}
