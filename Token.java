public class Token {
    public enum tokenType { IDENTIFIER, NUMBER, ENDOFLINE, WHILE, DEFINE, ARRAY, OF, STRING, CONSTANTS, VARIABLES, INTEGER, FOR, FROM, TO,
                            WRITE, REAL, BOOLEAN, CHARACTER, VAR, IF, THEN, MOD, ELSIF, ELSE, REPEAT, UNTIL, NOT, AND, OR, READ, LEFT,
                            RIGHT, SUBSTRING, SQUAREROOT, GETRANDOM, INTEGERTOREAL, REALTOINTEGER, START, END, COMMA, COMPARISONEQUAL, PLUS,
                            MINUS, DIVIDE, RIGHTPARENTHESES, LEFTBRACKET, RIGHTBRACKET, LEFTCURLYBRACE, RIGHTCURLYBRACE, QUOTATION, SEMICOLON,
                            COLON, ASSIGNMENTEQUAL, LESSTHANOREQUALTO, NOTEQUAL, LESSTHAN, GREATERTHANOREQUALTO, GREATERTHAN, LEFTPARENTHESES, 
                            MULTIPLY, STRINGLITERAL, CHARACTERLITERAL, INVALIDSYMBOL, UNTERMINATEDSTRING, SPACE, DEDENT, INDENT }

    private tokenType type;
    private String value;
    private int lineNumber;

    public Token() {
    }

    public void setValue(String inputValue) {
        value = inputValue;
    }

    public void setToken(tokenType inputToken) {
        type = inputToken;
    }

    public void setLineNumber(int inputLineNumber) {
        lineNumber = inputLineNumber;
    }

    public tokenType getToken() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}