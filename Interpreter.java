import java.util.HashMap;

import java.util.ArrayList;

public class Interpreter {

    private void AssignmentNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, AssignmentNode inputAssignmentNode) {
        Node assignmentNode = expression(inputLocalVariableMap, inputAssignmentNode);
    }

    private BooleanDataType booleanCompareNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, BooleanCompareNode inputBooleanCompareNode) {
        Node leftChild = expression(inputLocalVariableMap, inputBooleanCompareNode.getLeftChild());
        Node rightChild = expression(inputLocalVariableMap, inputBooleanCompareNode.getRightChild());
        boolean value = false;
        if (leftChild instanceof IntegerNode) {
            IntegerNode leftIntChild = (IntegerNode) leftChild;
            if (rightChild instanceof IntegerNode) {
                IntegerNode rightIntChild = (IntegerNode) rightChild;
                if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.EQUAL) {
                    value = (leftIntChild.getValue() == rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    value = (leftIntChild.getValue() > rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO) {
                    value = (leftIntChild.getValue() >= rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    value = (leftIntChild.getValue() < rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHANOREQUALTO) {
                    value = (leftIntChild.getValue() <= rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.NOTEQUAL) {
                    value = (leftIntChild.getValue() != rightIntChild.getValue()) ? true : false;
                }
                else {
                    System.out.println("ERROR: Incorrect comparison operator given for boolean compare.");
                    System.exit(6);
                }
            }
            else if (rightChild instanceof RealNode) {
                RealNode rightRealChild = (RealNode) rightChild;
                if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.EQUAL) {
                    value = (leftIntChild.getValue() == rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    value = (leftIntChild.getValue() > rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO) {
                    value = (leftIntChild.getValue() >= rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    value = (leftIntChild.getValue() < rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHANOREQUALTO) {
                    value = (leftIntChild.getValue() <= rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.NOTEQUAL) {
                    value = (leftIntChild.getValue() != rightRealChild.getValue()) ? true : false;
                }
                else {
                    System.out.println("ERROR: Incorrect comparison operator given for boolean compare.");
                    System.exit(6);
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type on right side of boolean compare.");
                System.exit(4);
            }
        }
        else if (leftChild instanceof RealNode) {
            RealNode leftRealChild = (RealNode) leftChild;
            if (rightChild instanceof IntegerNode) {
                IntegerNode rightIntChild = (IntegerNode) rightChild;
                if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.EQUAL) {
                    value = (leftRealChild.getValue() == rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    value = (leftRealChild.getValue() > rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO) {
                    value = (leftRealChild.getValue() >= rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    value = (leftRealChild.getValue() < rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHANOREQUALTO) {
                    value = (leftRealChild.getValue() <= rightIntChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.NOTEQUAL) {
                    value = (leftRealChild.getValue() != rightIntChild.getValue()) ? true : false;
                }
                else {
                    System.out.println("ERROR: Incorrect comparison operator given for boolean compare.");
                    System.exit(6);
                }
            }
            else if (rightChild instanceof RealNode) {
                RealNode rightRealChild = (RealNode) rightChild;
                if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.EQUAL) {
                    value = (leftRealChild.getValue() == rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHAN) {
                    value = (leftRealChild.getValue() > rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.GREATERTHANOREQUALTO) {
                    value = (leftRealChild.getValue() >= rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHAN) {
                    value = (leftRealChild.getValue() < rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.LESSTHANOREQUALTO) {
                    value = (leftRealChild.getValue() <= rightRealChild.getValue()) ? true : false;
                }
                else if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.NOTEQUAL) {
                    value = (leftRealChild.getValue() != rightRealChild.getValue()) ? true : false;
                }
                else {
                    System.out.println("ERROR: Incorrect comparison operator given for boolean compare.");
                    System.exit(6);
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type on right side of boolean compare.");
                System.exit(5);
            }
        }
        else {
            System.out.println("ERROR: Incorrect data type on left side of boolean compare.");
            System.exit(3);
        }
        BooleanDataType newBooleanData = new BooleanDataType(value, false);
        return newBooleanData;
    }

    private Node expression(HashMap<String, InterpreterDataType> inputLocalVariableMap, Node inputNode) {
        if (inputNode instanceof MathOpNode) {
            MathOpNode currentMathOpNode = (MathOpNode) inputNode;
            if (currentMathOpNode.getStringValue() != null) {
                StringNode newStringNode = new StringNode(currentMathOpNode.getStringValue());
                return newStringNode;
            }
            else if (currentMathOpNode.isReal()) {
                RealNode newRealNode = new RealNode(currentMathOpNode.getRealValue());
                return newRealNode;
            }
            else {
                IntegerNode newIntegerNode = new IntegerNode(currentMathOpNode.getIntValue());
                return newIntegerNode;
            }
        }
        else if (inputNode instanceof IntegerNode || inputNode instanceof RealNode || inputNode instanceof StringNode) {
            return inputNode;
        }
        else if (inputNode instanceof VariableReferenceNode) {
            
        }
        else if (inputNode instanceof AssignmentNode) {
            AssignmentNode currentAssignmentNode = (AssignmentNode) inputNode;
            String assignmentTarget = currentAssignmentNode.getTarget().getName();
            Node assignmentValue = currentAssignmentNode.getValue();
            if (assignmentValue instanceof IntegerNode) {
                IntegerNode currentIntegerNode = (IntegerNode) assignmentValue;
                IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newIntegerData);
            }
            else if (assignmentValue instanceof RealNode) {
                RealNode currentRealNode = (RealNode) assignmentValue;
                RealDataType newRealData = new RealDataType(currentRealNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newRealData);
            }
            else if (assignmentValue instanceof CharacterNode) {
                CharacterNode currentCharacterNode = (CharacterNode) assignmentValue;
                CharacterDataType newCharacterData = new CharacterDataType(currentCharacterNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newCharacterData);
            }
            else if (assignmentValue instanceof StringNode) {
                StringNode currentStringNode = (StringNode) assignmentValue;
                StringDataType newStringData = new StringDataType(currentStringNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newStringData);
            }
            else if (assignmentValue instanceof BooleanNode) {
                BooleanNode currentBooleanNode = (BooleanNode) assignmentValue;
                BooleanDataType newBooleanData = new BooleanDataType(currentBooleanNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newBooleanData);
            }
            else if (assignmentValue instanceof BooleanCompareNode) {
                BooleanCompareNode currentBooleanCompareNode = (BooleanCompareNode) assignmentValue;
                BooleanDataType newBooleanCompareData = booleanCompareNodeFunction(inputLocalVariableMap, currentBooleanCompareNode);
                inputLocalVariableMap.put(assignmentTarget, newBooleanCompareData);
            }
        }
        return null;
    }

    private void interpretBlock(HashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<StatementNode> inputStatementArray) throws SyntaxErrorException {
        for (int i = 0; i < inputStatementArray.size(); i++) {
            if (inputStatementArray.get(i) instanceof AssignmentNode) {
                AssignmentNode newAssignmentNode = (AssignmentNode) inputStatementArray.get(i);
                AssignmentNodeFunction(inputLocalVariableMap, newAssignmentNode);
            }
            else if (inputStatementArray.get(i) instanceof BooleanCompareNode) {
                BooleanCompareNode newBooleanCompareNode = (BooleanCompareNode) inputStatementArray.get(i);
                BooleanDataType booleanCompareNodeData = booleanCompareNodeFunction(inputLocalVariableMap, newBooleanCompareNode);
            }
            else if (inputStatementArray.get(i) instanceof BooleanNode) {
                BooleanNode newBooleanNode = (BooleanNode) inputStatementArray.get(i);
                BooleanDataType booleanNodeData = new BooleanDataType(newBooleanNode.getValue(), false);
            }
            else if (inputStatementArray.get(i) instanceof CharacterNode) {
                CharacterNode newCharacterNode = (CharacterNode) inputStatementArray.get(i);
                CharacterDataType characterNodeData = new CharacterDataType(newCharacterNode.getValue(), false);
            }
            else if (inputStatementArray.get(i) instanceof ForNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof FunctionCallNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof IfNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof IntegerNode) {
                IntegerNode newIntegerNode = (IntegerNode) inputStatementArray.get(i);
                IntegerDataType integerNodeData = new IntegerDataType(newIntegerNode.getValue(), false);
            }
            else if (inputStatementArray.get(i) instanceof MathOpNode) {
                MathOpNode newMathOpNode = (MathOpNode) inputStatementArray.get(i);
                InterpreterDataType newMathOpData = MathOpNodeFunction(inputLocalVariableMap, newMathOpNode);
            }
            else if (inputStatementArray.get(i) instanceof RealNode) {
                RealNode newRealNode = (RealNode) inputStatementArray.get(i);
                RealDataType realNodeData = new RealDataType(newRealNode.getValue(), false);
            }
            else if (inputStatementArray.get(i) instanceof RepeatNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof StringNode) {
                StringNode newStringNode = (StringNode) inputStatementArray.get(i);
                StringDataType stringNodeData = new StringDataType(newStringNode.getValue(), false);
            }
            else if (inputStatementArray.get(i) instanceof VariableReferenceNode) {
                VariableReferenceNode newVariableReferenceNode = (VariableReferenceNode) inputStatementArray.get(i);
                InterpreterDataType newVariableData = VariableReferenceNodeFunction(inputLocalVariableMap, newVariableReferenceNode);
            }
            else if (inputStatementArray.get(i) instanceof WhileNode) {
                WhileNode newWhileNode = (WhileNode) inputStatementArray.get(i);
            }
            else {
                System.out.println("ERROR: Statement Node type not detected.");
                System.exit(2);
            }
        }
    }

    private void interpretFunction(FunctionNode inputFunctionNode) throws SyntaxErrorException {
        HashMap<String, InterpreterDataType> localVariableMap = new HashMap<String, InterpreterDataType>();
        ArrayList<VariableNode> variableArray = inputFunctionNode.getVariableArray();
        ArrayList<StatementNode> statementArray = inputFunctionNode.getStatementArray();
        for (int i = 0; i < variableArray.size(); i++) {
            VariableNode currentVariableNode = variableArray.get(i);
            String name = currentVariableNode.getName();
            switch (currentVariableNode.getType()) {
                case INTEGER:
                    IntegerNode newIntNode = (IntegerNode) currentVariableNode.getValue();
                    IntegerDataType newIntegerData = new IntegerDataType(newIntNode.getValue(), currentVariableNode.getChangeable());
                    localVariableMap.put(name, newIntegerData);
                    break;
                case REAL:
                    RealNode newRealNode = (RealNode) currentVariableNode.getValue();
                    RealDataType newRealData = new RealDataType(newRealNode.getValue(), currentVariableNode.getChangeable());
                    localVariableMap.put(name, newRealData);
                    break;
                case CHARACTER:
                    CharacterNode newCharNode = (CharacterNode) currentVariableNode.getValue();
                    CharacterDataType newCharData = new CharacterDataType(newCharNode.getValue(), currentVariableNode.getChangeable());
                    localVariableMap.put(name, newCharData);
                    break;
                case STRING:
                    StringNode newStringNode = (StringNode) currentVariableNode.getValue();
                    StringDataType newStringData = new StringDataType(newStringNode.getValue(), currentVariableNode.getChangeable());
                    localVariableMap.put(name, newStringData);
                    break;
                case BOOLEAN:
                    BooleanNode newBooleanNode = (BooleanNode) currentVariableNode.getValue();
                    BooleanDataType newBooleanData = new BooleanDataType(newBooleanNode.getValue(), currentVariableNode.getChangeable());
                    localVariableMap.put(name, newBooleanData);
                    break;
                case ARRAY:
                    int startIndex = currentVariableNode.getIntFrom();
                    int endIndex = currentVariableNode.getIntTo();
                    ArrayDataType newArrayData = null;
                    switch (currentVariableNode.getArrayType()) {
                        case INTEGER:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, startIndex, endIndex, currentVariableNode.getChangeable());
                            break;
                        case REAL:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, startIndex, endIndex, currentVariableNode.getChangeable());
                            break;
                        case CHARACTER:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, startIndex, endIndex, currentVariableNode.getChangeable());
                            break;
                        case BOOLEAN:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, startIndex, endIndex, currentVariableNode.getChangeable());
                            break;
                        case STRING:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, startIndex, endIndex, currentVariableNode.getChangeable());
                            break;
                        default:
                            System.out.println("Error: Array type not detected.");
                            System.exit(1);
                    }
                    localVariableMap.put(name, newArrayData);
                    break;
                default:
                    System.out.println("ERROR: Variable type not detected.");
                    System.exit(0);
                    break;
            }
        }
        interpretBlock(localVariableMap, statementArray);
    }

    private InterpreterDataType MathOpNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, MathOpNode inputMathOpNode) throws SyntaxErrorException {
        Node leftChildNode = expression(inputLocalVariableMap, inputMathOpNode.getLeftChild());
        Node rightChildNode = expression(inputLocalVariableMap, inputMathOpNode.getRightChild());
        if (leftChildNode instanceof StringNode && rightChildNode instanceof StringNode) {
            if (inputMathOpNode.getType() != MathOpNode.operationType.ADD)
                throw new SyntaxErrorException(null);
            StringDataType newStringData = new StringDataType(inputMathOpNode.getStringValue(), false);
            return newStringData;
        }
        else if (leftChildNode instanceof RealNode && rightChildNode instanceof RealNode) {
            RealDataType newRealData = new RealDataType(inputMathOpNode.getRealValue(), false);
            return newRealData;
        }
        else if (leftChildNode instanceof IntegerNode && rightChildNode instanceof IntegerNode) {
            IntegerDataType newIntegerData = new IntegerDataType(inputMathOpNode.getIntValue(), false);
            return newIntegerData;
        }
        else
            return null;
    }

    private InterpreterDataType VariableReferenceNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, VariableReferenceNode inputVariableReferenceNode) throws SyntaxErrorException {
        InterpreterDataType variableMapKeyValue = inputLocalVariableMap.get(inputVariableReferenceNode.getName());
        if (variableMapKeyValue == null) {
            throw new SyntaxErrorException(null);
        }
        return variableMapKeyValue;
    }
}
