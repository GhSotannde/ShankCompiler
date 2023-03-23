public class CharacterNode extends StatementNode {
    private char character;

    public CharacterNode(char inputChar) {
        character = inputChar;
    }

    public char getValue() {
        return character;
    }

    public String ToString() {
        return "CharacterNode(" + character + ")";
    }
}
