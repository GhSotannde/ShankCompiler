public class StringNode extends StatementNode {
    private String string;

    public StringNode(String inputString) {
        string = inputString;
    } 

    public String getValue() {
        return string;
    }

    public String ToString() {
        return "StringNode(" + string + ")";
    }
}
