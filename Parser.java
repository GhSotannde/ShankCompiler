import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokenArray = new ArrayList<Token>();
    
    private Token newToken = new Token();
    
    public Parser(ArrayList<Token> inputTokenArray) {
        tokenArray = inputTokenArray;
    }

    public Node parse() throws SyntaxErrorException {
        Node newNode;
        ProgramNode programNode = new ProgramNode();
        Token endOfLineNode;
        do {
            newNode = expression();
            if (newNode != null) { //Then we have an expression
                if (newNode instanceof MathOpNode) {
                    MathOpNode expressionNode = (MathOpNode) newNode;
                    System.out.println(expressionNode.ToString());
                }
                else if (newNode instanceof IntegerNode) {
                    IntegerNode integerNode = (IntegerNode) newNode;
                    System.out.println(integerNode.ToString());
                }
                else if (newNode instanceof RealNode) {
                    RealNode realNode = (RealNode) newNode;
                    System.out.println(realNode.ToString());
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else {
                newNode = function();
                if (newNode != null) {
                    FunctionNode functionNode = (FunctionNode) newNode;
                    programNode.addToFunctionMap(functionNode);
                }
            }
            endOfLineNode = expectEndsOfLine();
        } while (newNode != null && endOfLineNode != null); 
        return programNode;
    }

    private void addVariableNodesToArray(int inputChangeable, ArrayList<VariableNode> inputVariableNodeArray) throws SyntaxErrorException {
        Token currentToken = matchAndRemove(Token.tokenType.IDENTIFIER);
        if (currentToken != null) {
            VariableNode.variableType variableType = searchForType(); //Looks ahead for variable's data type
            if (variableType == null)
                throw new SyntaxErrorException(tokenArray.get(0));
            inputVariableNodeArray.add(new VariableNode(currentToken.getValue(), variableType, 1));
            while (matchAndRemove(Token.tokenType.COMMA) != null) { //Checks for another variable
                currentToken = matchAndRemove(Token.tokenType.IDENTIFIER);
                if (currentToken != null) {
                    variableType = searchForType();
                    if (variableType == null)
                        throw new SyntaxErrorException(tokenArray.get(0));
                    inputVariableNodeArray.add(new VariableNode(currentToken.getValue(), variableType, 1));
                }
            }
            if (matchAndRemove(Token.tokenType.COLON) != null) { //Clears out the type and punctuation tokens following variable names
                matchAndRemove(Token.tokenType.INTEGER);
                matchAndRemove(Token.tokenType.REAL);
                matchAndRemove(Token.tokenType.CHARACTER);
                matchAndRemove(Token.tokenType.ARRAY);
                matchAndRemove(Token.tokenType.BOOLEAN);
                matchAndRemove(Token.tokenType.SEMICOLON);
            }
        }
    }

    private void addConstantToArray(ArrayList<VariableNode> inputVariableNodeArray) throws SyntaxErrorException {
        int negativeMultiplier = 1;
        float numberValue;
        Token currentToken;
        do {
            currentToken = matchAndRemove(Token.tokenType.IDENTIFIER);
            negativeMultiplier = 1;
            if (currentToken != null) {
                String functionName = currentToken.getValue();
                if (matchAndRemove(Token.tokenType.COMPARISONEQUAL) != null) {
                    currentToken = matchAndRemove(Token.tokenType.MINUS);
                    if (currentToken != null) {
                        negativeMultiplier = -1;
                    }
                    if (peek(0).getToken() == Token.tokenType.NUMBER) {
                        currentToken = matchAndRemove(Token.tokenType.NUMBER);
                        numberValue = Float.parseFloat(currentToken.getValue());
                        if (numberValue % 1 == 0) { //if number divided by 1 does not give a remainder, its an integer
                            IntegerNode newIntegerNode = new IntegerNode((int) numberValue * negativeMultiplier);
                            inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.INTEGER, 0, newIntegerNode));
                        }
                        else {
                            RealNode newRealNode = new RealNode(numberValue * negativeMultiplier);
                            inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.REAL, 0, newRealNode));
                        }
                    }
                    else if (peek(0).getToken() == Token.tokenType.CHARACTERLITERAL) {
                        currentToken = matchAndRemove(Token.tokenType.CHARACTERLITERAL);
                        CharacterNode newCharacterNode = new CharacterNode(currentToken.getValue().charAt(0));
                        inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.CHARACTER, 0, newCharacterNode));
                    }
                    else if (peek(0).getToken() == Token.tokenType.STRINGLITERAL) {
                        currentToken = matchAndRemove(Token.tokenType.STRINGLITERAL);
                        StringNode newStringNode = new StringNode(currentToken.getValue());
                        inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.STRING, 0, newStringNode));
                    }
                    else if (peek(0).getToken() == Token.tokenType.TRUE) {
                        currentToken = matchAndRemove(Token.tokenType.TRUE);
                        BooleanNode newBooleanNode = new BooleanNode(true);
                        inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.BOOLEAN, 0, newBooleanNode));
                    }
                    else if (peek(0).getToken() == Token.tokenType.FALSE) {
                        currentToken = matchAndRemove(Token.tokenType.FALSE);
                        BooleanNode newBooleanNode = new BooleanNode(false);
                        inputVariableNodeArray.add(new VariableNode(functionName, VariableNode.variableType.BOOLEAN, 0, newBooleanNode));
                    }
                    else {
                        throw new SyntaxErrorException(tokenArray.get(0));
                    }
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
        currentToken = matchAndRemove(Token.tokenType.COMMA); //If a comma follows the data, another constant should exist
        } while (currentToken != null);
    }

    private Node boolCompare() throws SyntaxErrorException {
        Node leftNode = expression();

        if (leftNode instanceof IntegerNode) { 
            IntegerNode left = (IntegerNode) leftNode;
            Token currentToken = matchAndRemove(Token.tokenType.LESSTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0; 
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.LESSTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.COMPARISONEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.NOTEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            return leftNode;
        }
        else if (leftNode instanceof RealNode) {
            RealNode left = (RealNode) leftNode;
            Token currentToken = matchAndRemove(Token.tokenType.LESSTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.LESSTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.COMPARISONEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.NOTEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            return leftNode;
        }
        else if (leftNode instanceof MathOpNode) {
            MathOpNode left = (MathOpNode) leftNode;
            Token currentToken = matchAndRemove(Token.tokenType.LESSTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.LESSTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.COMPARISONEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.NOTEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            return leftNode;
        }
        else if (leftNode instanceof VariableReferenceNode) {
            VariableReferenceNode left = (VariableReferenceNode) leftNode;
            Token currentToken = matchAndRemove(Token.tokenType.LESSTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() < right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHAN);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() > right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHAN, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.LESSTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() <= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.LESSTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.GREATERTHANOREQUALTO);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() >= right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.COMPARISONEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() == right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.EQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            currentToken = matchAndRemove(Token.tokenType.NOTEQUAL);
            if (currentToken != null) {
                Node rightNode = expression();
                if (rightNode instanceof IntegerNode) {
                    IntegerNode right = (IntegerNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof RealNode) {
                    RealNode right = (RealNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof MathOpNode) {
                    MathOpNode right = (MathOpNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else if (rightNode instanceof VariableReferenceNode) {
                    VariableReferenceNode right = (VariableReferenceNode) rightNode;
                    Integer value = (left.getValue() != right.getValue()) ? 1 : 0;
                    BooleanCompareNode newBooleanCompareNode = new BooleanCompareNode(BooleanCompareNode.comparisonType.NOTEQUAL, left, right, value);
                    return newBooleanCompareNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            return leftNode;
        }
        else {
            return null;
        }
    }

    private MathOpNode createMathOpNode(MathOpNode.operationType inputOperationType, Node inputLeftChild, Node inputRightChild) throws SyntaxErrorException {
        MathOpNode newMathOpNode;
        if (inputLeftChild == null) {
            throw new SyntaxErrorException(tokenArray.get(0));
        }
        else if (inputLeftChild instanceof IntegerNode) {
            IntegerNode leftIntegerNode = (IntegerNode) inputLeftChild;
            if (inputRightChild == null) {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
            else if (inputRightChild instanceof IntegerNode) {
                IntegerNode rightIntegerNode = (IntegerNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftIntegerNode, rightIntegerNode);
                //Combines the two child nodes into one MathOpNode and calculates its value based on the value of its children and assigned operator
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() + rightIntegerNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftIntegerNode.getValue() - rightIntegerNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftIntegerNode.getValue() * rightIntegerNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftIntegerNode.getValue() / rightIntegerNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() % rightIntegerNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof RealNode) {
                RealNode rightRealNode = (RealNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftIntegerNode, rightRealNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() + rightRealNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftIntegerNode.getValue() - rightRealNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftIntegerNode.getValue() * rightRealNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftIntegerNode.getValue() / rightRealNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() % rightRealNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof MathOpNode) {
                MathOpNode rightMathOpNode = (MathOpNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftIntegerNode, rightMathOpNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() + rightMathOpNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftIntegerNode.getValue() - rightMathOpNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftIntegerNode.getValue() * rightMathOpNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftIntegerNode.getValue() / rightMathOpNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftIntegerNode.getValue() % rightMathOpNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
        }
        else if (inputLeftChild instanceof RealNode) {
            RealNode leftRealNode = (RealNode) inputLeftChild;
            if (inputRightChild == null) {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
            else if (inputRightChild instanceof IntegerNode) {
                IntegerNode rightIntegerNode = (IntegerNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftRealNode, rightIntegerNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftRealNode.getValue() + rightIntegerNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftRealNode.getValue() - rightIntegerNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftRealNode.getValue() * rightIntegerNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftRealNode.getValue() / rightIntegerNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftRealNode.getValue() % rightIntegerNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof RealNode) {
                RealNode rightRealNode = (RealNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftRealNode, rightRealNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftRealNode.getValue() + rightRealNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftRealNode.getValue() - rightRealNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftRealNode.getValue() * rightRealNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftRealNode.getValue() / rightRealNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftRealNode.getValue() % rightRealNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof MathOpNode) {
                MathOpNode rightMathOpNode = (MathOpNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftRealNode, rightMathOpNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftRealNode.getValue() + rightMathOpNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftRealNode.getValue() - rightMathOpNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftRealNode.getValue() * rightMathOpNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftRealNode.getValue() / rightMathOpNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftRealNode.getValue() % rightMathOpNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
        }
        else if (inputLeftChild instanceof MathOpNode) {
            MathOpNode leftMathOpNode = (MathOpNode) inputLeftChild;
            if (inputRightChild == null) {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
            else if (inputRightChild instanceof IntegerNode) {
                IntegerNode rightIntegerNode = (IntegerNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftMathOpNode, rightIntegerNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() + rightIntegerNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftMathOpNode.getValue() - rightIntegerNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftMathOpNode.getValue() * rightIntegerNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftMathOpNode.getValue() / rightIntegerNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() % rightIntegerNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof RealNode) {
                RealNode rightRealNode = (RealNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftMathOpNode, rightRealNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() + rightRealNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftMathOpNode.getValue() - rightRealNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftMathOpNode.getValue() * rightRealNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftMathOpNode.getValue() / rightRealNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() % rightRealNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else if (inputRightChild instanceof MathOpNode) {
                MathOpNode rightMathOpNode = (MathOpNode) inputRightChild;
                newMathOpNode = new MathOpNode(inputOperationType, leftMathOpNode, rightMathOpNode);
                switch (inputOperationType) {
                    case ADD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() + rightMathOpNode.getValue());
                        break;
                    case SUBTRACT:
                        newMathOpNode.setValue(leftMathOpNode.getValue() - rightMathOpNode.getValue());
                        break;
                    case MULTIPLY:
                        newMathOpNode.setValue(leftMathOpNode.getValue() * rightMathOpNode.getValue());
                        break;
                    case DIVIDE:
                        newMathOpNode.setValue(leftMathOpNode.getValue() / rightMathOpNode.getValue());
                        break;
                    case MOD:
                        newMathOpNode.setValue(leftMathOpNode.getValue() % rightMathOpNode.getValue());
                        break;
                    default:
                        break;
                }
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
        }
        else {
            throw new SyntaxErrorException(tokenArray.get(0));
        }

        return newMathOpNode;
    }

    private Token expectEndsOfLine() throws SyntaxErrorException {
        Token currentToken = matchAndRemove(Token.tokenType.ENDOFLINE);
        if (currentToken != null) {
            while (tokenArray.size() > 0 && matchAndRemove(Token.tokenType.ENDOFLINE) != null) { //Eats up any additional ENDOFLINE tokens
            }
            return currentToken;
        }
        else {
            return null;
        }
    }

    private Node expression() throws SyntaxErrorException {
        MathOpNode newMathOpNode;
        newMathOpNode = new MathOpNode();
        Node leftNode = term();
        if (leftNode == null) {
            return null;
        }
        if (matchAndRemove(Token.tokenType.PLUS) != null) {
            Node rightNode = term();
            newMathOpNode = createMathOpNode(MathOpNode.operationType.ADD, leftNode, rightNode);
        }
        else if (matchAndRemove(Token.tokenType.MINUS) != null) {
            Node rightNode = term();
            newMathOpNode = createMathOpNode(MathOpNode.operationType.SUBTRACT, leftNode, rightNode);
        }
        else {
            return leftNode; //If a factor is not succeeded by an operator, return it
        }
        while (tokenArray.size() > 0 && (peek(0).getToken() == Token.tokenType.PLUS 
        || peek(0).getToken() == Token.tokenType.MINUS)) { //While loop checks if current token without removing it from array, allowing it to be mathchAndRemove'd later
            if (matchAndRemove(Token.tokenType.PLUS) != null) {
                Node rightNode = term();
                newMathOpNode = createMathOpNode(MathOpNode.operationType.ADD, newMathOpNode, rightNode);
            }
            else if (matchAndRemove(Token.tokenType.MINUS) != null) {
                Node rightNode = term();
                newMathOpNode = createMathOpNode(MathOpNode.operationType.SUBTRACT, newMathOpNode, rightNode);
            }
        }
        return newMathOpNode;
    }

    private Node factor() throws SyntaxErrorException {
        float numberValue;
        int negativeMultiplier = 1;
        if (matchAndRemove(Token.tokenType.MINUS) != null) {
            negativeMultiplier = -1;
        }
        newToken = matchAndRemove(Token.tokenType.NUMBER);
        if (newToken != null) {
            numberValue = Float.parseFloat(newToken.getValue());
            if (numberValue % 1 == 0) { //Check if number is float or integer
                IntegerNode newIntegerNode = new IntegerNode((int) numberValue * negativeMultiplier);
                return newIntegerNode;
            }
            else {
                RealNode newRealNode = new RealNode(numberValue * negativeMultiplier);
                return newRealNode;
            }
        }
        if (matchAndRemove(Token.tokenType.LEFTPARENTHESES) != null) {
            //MathOpNode newMathOpNode = (MathOpNode) expression();
            Node currentNode = boolCompare();
            if (currentNode instanceof IntegerNode) {
                currentNode = (IntegerNode) currentNode;
                if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
                    return currentNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else if (currentNode instanceof RealNode) {
                currentNode = (RealNode) currentNode;
                if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
                    return currentNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else if (currentNode instanceof MathOpNode) {
                currentNode = (MathOpNode) currentNode;
                if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
                    return currentNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else if (currentNode instanceof VariableReferenceNode) {
                currentNode = (VariableReferenceNode) currentNode;
                if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
                    return currentNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            else if (currentNode instanceof BooleanCompareNode) {
                currentNode = (BooleanCompareNode) currentNode;
                if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
                    return currentNode;
                }
                else {
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
            }
            return null;
        }
        newToken = matchAndRemove(Token.tokenType.IDENTIFIER);
        if (newToken != null) {
            VariableReferenceNode newVariableReferenceNode = new VariableReferenceNode(newToken.getValue());
            return newVariableReferenceNode;
        }
        return null;
    }

    private FunctionNode function() throws SyntaxErrorException {
        if (matchAndRemove(Token.tokenType.DEFINE) != null) {
            Token currentToken = matchAndRemove(Token.tokenType.IDENTIFIER);
            if (currentToken != null) {
                String functionName = currentToken.getValue();
                currentToken = matchAndRemove(Token.tokenType.LEFTPARENTHESES);
                if (currentToken != null) {
                    ArrayList<VariableNode> parameterArray = parameterDeclarations(); //Records parameters
                    ArrayList<VariableNode> variableArray = new ArrayList<VariableNode>();
                    variableArray = variableDeclarations(variableArray); //Records variables 
                    currentToken = expectEndsOfLine();
                    if (currentToken == null)
                        throw new SyntaxErrorException(tokenArray.get(0));
                    if (matchAndRemove(Token.tokenType.INDENT) != null) {
                        statementDeclarations(); //Prints statements
                        if (matchAndRemove(Token.tokenType.DEDENT) != null) {
                            FunctionNode functionNode = new FunctionNode(functionName, parameterArray, variableArray, null);
                            return functionNode;
                        }
                        throw new SyntaxErrorException(tokenArray.get(0));
                    }
                    throw new SyntaxErrorException(tokenArray.get(0));
                }
                throw new SyntaxErrorException(tokenArray.get(0));
            }
            throw new SyntaxErrorException(tokenArray.get(0));
        }
        return null;
    }

    private Token matchAndRemove(Token.tokenType inputTokenType) {
        if (tokenArray.size() > 0) {
            Token currentToken = tokenArray.get(0);
            if (inputTokenType == currentToken.getToken()) {
                tokenArray.remove(0);
                return currentToken;
            }
        }
        return null;
    }

    private ArrayList<VariableNode> parameterDeclarations() throws SyntaxErrorException {
        ArrayList<VariableNode> variableNodeArray = new ArrayList<VariableNode>();
        while (peek(0).getToken() != Token.tokenType.RIGHTPARENTHESES) {
            Token currentToken;
            currentToken = matchAndRemove(Token.tokenType.VAR);
            if (currentToken != null) {
                addVariableNodesToArray(1, variableNodeArray); //Variable is changeable
            }
            else {
                addVariableNodesToArray(0, variableNodeArray); //Variable can not be changed
            }
        }
        if (matchAndRemove(Token.tokenType.RIGHTPARENTHESES) != null) {
            if (variableNodeArray.size() == 0) {
                return null;
            }
            return variableNodeArray;
        }
        throw new SyntaxErrorException(tokenArray.get(0));
    }

    private Token peek(int inputInteger) {
        Token token = tokenArray.get(inputInteger) != null ? tokenArray.get(inputInteger) : null;
        return token;
    }

    private VariableNode.variableType searchForType() {
        for (int i = 0; i < 10; i++) { //Searches up to 10 tokens away for the variable type of the current variable
            switch (peek(i).getToken()) {
                case INTEGER:
                    return VariableNode.variableType.INTEGER;
                case REAL:
                    return VariableNode.variableType.REAL;
                case CHARACTER:
                    return VariableNode.variableType.CHARACTER;
                case STRING:
                    return VariableNode.variableType.STRING;
                case ARRAY:
                    return VariableNode.variableType.ARRAY;
                case BOOLEAN:
                    return VariableNode.variableType.BOOLEAN;
                default:
                    break;
            }
        }
        return null;
    }

    private void statementDeclarations() throws SyntaxErrorException {
        Node currentNode = expression();
            while (currentNode != null) { //Loops until end of statement lines
                if (currentNode instanceof MathOpNode) {
                    MathOpNode expressionNode = (MathOpNode) currentNode;
                    System.out.println(expressionNode.ToString());
                }
                else if (currentNode instanceof IntegerNode) {
                    IntegerNode integerNode = (IntegerNode) currentNode;
                    System.out.println(integerNode.ToString());
                }
                else if (currentNode instanceof RealNode) {
                    RealNode realNode = (RealNode) currentNode;
                    System.out.println(realNode.ToString());
                }
                Token currentToken = expectEndsOfLine();
                if (currentToken == null)
                    throw new SyntaxErrorException(tokenArray.get(0));
                while (peek(0).getToken() == Token.tokenType.INDENT) { //Eats up indent tokens between statement lines
                    matchAndRemove(Token.tokenType.INDENT);
                }
                currentNode = expression();
            }
    }

    private Node term() throws SyntaxErrorException {
        MathOpNode newMathOpNode;
        newMathOpNode = new MathOpNode();
        Node leftNode = factor();
        if (leftNode == null) {
            return null;
        }
        if (matchAndRemove(Token.tokenType.MULTIPLY) != null) {
            Node rightNode = factor();
            newMathOpNode = createMathOpNode(MathOpNode.operationType.MULTIPLY, leftNode, rightNode);
        }
        else if (matchAndRemove(Token.tokenType.DIVIDE) != null) {
            Node rightNode = factor();
            newMathOpNode = createMathOpNode(MathOpNode.operationType.DIVIDE, leftNode, rightNode);
        }
        else if (matchAndRemove(Token.tokenType.MOD) != null) {
            Node rightNode = factor();
            newMathOpNode = createMathOpNode(MathOpNode.operationType.MOD, leftNode, rightNode);
        }
        else {
            return leftNode;
        }
        while (tokenArray.size() > 0 && (peek(0).getToken() == Token.tokenType.MULTIPLY
        || peek(0).getToken() == Token.tokenType.DIVIDE || peek(0).getToken() == Token.tokenType.MOD)) {
            if (matchAndRemove(Token.tokenType.MULTIPLY) != null) {
                Node rightNode = factor();
                newMathOpNode = createMathOpNode(MathOpNode.operationType.MULTIPLY, newMathOpNode, rightNode);
            }
            else if (matchAndRemove(Token.tokenType.DIVIDE) != null) {
                Node rightNode = factor();
                newMathOpNode = createMathOpNode(MathOpNode.operationType.DIVIDE, newMathOpNode, rightNode);
            }
            else if (matchAndRemove(Token.tokenType.MOD) != null) {
                Node rightNode = factor();
                newMathOpNode = createMathOpNode(MathOpNode.operationType.MOD, newMathOpNode, rightNode);
            }
        }
        return newMathOpNode;
    }
    
    private ArrayList<VariableNode> variableDeclarations(ArrayList<VariableNode> inputVariableArray) throws SyntaxErrorException {
        while (tokenArray.size() > 1 && (peek(1).getToken() == Token.tokenType.VARIABLES || peek(1).getToken() == Token.tokenType.CONSTANTS)) {
            Token currentToken = expectEndsOfLine();
            if (currentToken == null)
                throw new SyntaxErrorException(tokenArray.get(0));
            if (peek(0).getToken() == Token.tokenType.VARIABLES) {
                matchAndRemove(Token.tokenType.VARIABLES);
                addVariableNodesToArray(1, inputVariableArray);
            }
            else if (peek(0).getToken() == Token.tokenType.CONSTANTS) {
                matchAndRemove(Token.tokenType.CONSTANTS);
                addConstantToArray(inputVariableArray);
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }
        }
        return inputVariableArray;
    }
}
    