import java.util.ArrayList;

public class Parser {
    private ArrayList<Token> tokenArray = new ArrayList<Token>();
    private Token endOfLineNode;
    private Token newToken = new Token();
    
    public Parser(ArrayList<Token> inputTokenArray) {
        tokenArray = inputTokenArray;
    }

    public Node parse() throws SyntaxErrorException {
        Node newNode;
        do {
            newNode = expression();
            if (newNode != null) { //Then we have a number
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
            }
            else {
                newNode = function();
                if (newNode != null) {
                    System.out.println("Success!");
                }
                else {
                    break;
                }
            }
            endOfLineNode = expectEndsOfLine();
        } while (newNode != null && endOfLineNode != null);
        return newNode;
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
        Token newToken = matchAndRemove(Token.tokenType.ENDOFLINE);
        int endOfLineCounter = 0;
        if (newToken != null) {
            endOfLineCounter++;
            while (tokenArray.size() > 0 && matchAndRemove(Token.tokenType.ENDOFLINE) != null) {
                endOfLineCounter++;
            }
            return newToken;
        }
        if (endOfLineCounter == 0) {
            throw new SyntaxErrorException(tokenArray.get(0));
        }
        else {
            endOfLineCounter = 0;
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
        if (matchAndRemove(Token.tokenType.OPENPARENTHESES) != null) {
            MathOpNode newMathOpNode = (MathOpNode) expression();
            if (matchAndRemove(Token.tokenType.CLOSEPARENTHESES) != null) {
                return newMathOpNode;
            }
            else {
                throw new SyntaxErrorException(tokenArray.get(0));
            }

        }
        return null;
    }

    private FunctionNode function() {
        if (matchAndRemove(Token.tokenType.DEFINE) != null) {
            FunctionNode functionNode = new FunctionNode(null, null, null, null);
            return functionNode;
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

    private Token peek(int inputInteger) {
        Token token = tokenArray.get(inputInteger) != null ? tokenArray.get(inputInteger) : null;
        return token;
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
}
    