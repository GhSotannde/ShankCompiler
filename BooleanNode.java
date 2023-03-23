public class BooleanNode extends StatementNode {
    private boolean bool;

    public BooleanNode(boolean inputBool) {
        bool = inputBool;
    } 

    public boolean getValue() {
        return bool;
    }

    public String ToString() {
        return "BooleanNode(" + bool + ")";
    }
}
