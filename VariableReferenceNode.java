public class VariableReferenceNode extends StatementNode{
    
    private String name;
    private Node arrayIndexExpression = null;

    public VariableReferenceNode(String inputName) {
        name = inputName;
    }

    public VariableReferenceNode(String inputName, Node inputArrayIndexExpression) {
        name = inputName;
        arrayIndexExpression = inputArrayIndexExpression;
    }

    public String getName() {
        return name;
    }

    public Node getIndex() {
        if (arrayIndexExpression instanceof IntegerNode) {
            IntegerNode intNodeIndex = (IntegerNode) arrayIndexExpression;
            return intNodeIndex;
        }
        else if (arrayIndexExpression instanceof MathOpNode) {
            MathOpNode mathOpNodeIndex = (MathOpNode) arrayIndexExpression;
            if (mathOpNodeIndex.isInteger()) {
                IntegerNode intNodeIndex = new IntegerNode(mathOpNodeIndex.getIntValue());
                return intNodeIndex;
            }
            else {
                System.out.println("ERROR: Incorrect data type given for array index.");
                System.exit(0);
            }
        }
        else if (arrayIndexExpression instanceof VariableReferenceNode) {
            VariableReferenceNode newVariableReferenceNode = (VariableReferenceNode) arrayIndexExpression;
            return newVariableReferenceNode;
        }
        return null;
    }

    public String ToString() {
        String str = "VariableReferenceNode(Name:" + name;
        // Will call the ToString method for all nested expressions within array index
        if (arrayIndexExpression instanceof VariableReferenceNode) {
            VariableReferenceNode innerExpression = (VariableReferenceNode) arrayIndexExpression;
            str += ", Index: " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof IntegerNode) {
            IntegerNode innerExpression = (IntegerNode) arrayIndexExpression;
            str += ", Index: " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof RealNode) {
            RealNode innerExpression = (RealNode) arrayIndexExpression;
            str += ", Index: " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof MathOpNode) {
            MathOpNode innerExpression = (MathOpNode) arrayIndexExpression;
            str += ", Index: " + innerExpression.ToString();
        }
        else if (arrayIndexExpression instanceof BooleanCompareNode) {
            BooleanCompareNode innerExpression = (BooleanCompareNode) arrayIndexExpression;
            str += ", Index: " + innerExpression.ToString();
        }
        str += ")";
        return str;
    }
}
