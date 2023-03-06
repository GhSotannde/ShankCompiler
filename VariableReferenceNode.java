public class VariableReferenceNode extends Node{
    
    private String name;
    private float value;
    private Node arrayIndexExpression = null;

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
        return "VariableReferenceNode()";
    }
}
