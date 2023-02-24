public class StringNode extends Node {
    private String string;

    public StringNode(String inputString) {
        string = inputString;
    } 

    public String getString() {
        return string;
    }

    public String ToString() {
        return "StringNode(" + string + ")";
    }
}
