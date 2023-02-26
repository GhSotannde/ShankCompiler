public class CharacterNode extends Node {
    private char character;

    public CharacterNode(char inputChar) {
        character = inputChar;
    }

    public char getChar() {
        return character;
    }

    public String ToString() {
        return "CharacterNode(" + character + ")";
    }
}
