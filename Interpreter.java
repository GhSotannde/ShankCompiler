import java.util.HashMap;

import java.util.ArrayList;

public class Interpreter {

    private Node expression(Node inputNode) {
        if (inputNode instanceof MathOpNode) {
            MathOpNode currentMathOpNode = (MathOpNode) inputNode;
        }
        return null;
    }

    private void interpretBlock(HashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<StatementNode> inputStatementArray) throws SyntaxErrorException {
        for (int i = 0; i < inputStatementArray.size(); i++) {
            if (inputStatementArray.get(i) instanceof AssignmentNode) {

            }
            else if (inputStatementArray.get(i) instanceof BooleanCompareNode) {

            }
            else if (inputStatementArray.get(i) instanceof BooleanNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof CharacterNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof ForNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof FunctionCallNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof IfNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof IntegerNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof MathOpNode) {
                MathOpNode newMathOpNode = (MathOpNode) inputStatementArray.get(i);
                MathOpNodeFunction(newMathOpNode);
            }
            else if (inputStatementArray.get(i) instanceof ParameterNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof RealNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof RepeatNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof StringNode) {
                
            }
            else if (inputStatementArray.get(i) instanceof VariableReferenceNode) {
                VariableReferenceNode newVariableReferenceNode = (VariableReferenceNode) inputStatementArray.get(i);
                InterpreterDataType newVariableData = VariableReferenceNodeFunction(inputLocalVariableMap, newVariableReferenceNode);
            }
            else if (inputStatementArray.get(i) instanceof WhileNode) {
                
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
            Node newNode = currentVariableNode.getValue();
            switch (currentVariableNode.getType()) {
                case INTEGER:
                    newNode = currentVariableNode.getValue();
                    IntegerNode newIntNode = (IntegerNode) newNode;
                    int intValue = newIntNode.getValue();
                    IntegerDataType newIntegerData = new IntegerDataType(intValue, currentVariableNode.getChangeable());
                    localVariableMap.put(name, newIntegerData);
                    break;
                case REAL:
                    newNode = currentVariableNode.getValue();
                    RealNode newRealNode = (RealNode) newNode;
                    float realValue = newRealNode.getValue();
                    RealDataType newRealData = new RealDataType(realValue, currentVariableNode.getChangeable());
                    localVariableMap.put(name, newRealData);
                    break;
                case CHARACTER:
                    newNode = currentVariableNode.getValue();
                    CharacterNode newCharNode = (CharacterNode) newNode;
                    char CharValue = newCharNode.getValue();
                    CharacterDataType newCharData = new CharacterDataType(CharValue, currentVariableNode.getChangeable());
                    localVariableMap.put(name, newCharData);
                    break;
                case STRING:
                    newNode = currentVariableNode.getValue();
                    StringNode newStringNode = (StringNode) newNode;
                    String stringValue = newStringNode.getValue();
                    StringDataType newStringData = new StringDataType(stringValue, currentVariableNode.getChangeable());
                    localVariableMap.put(name, newStringData);
                    break;
                case BOOLEAN:
                    newNode = currentVariableNode.getValue();
                    BooleanNode newBooleanNode = (BooleanNode) newNode;
                    Boolean booleanValue = newBooleanNode.getValue();
                    BooleanDataType newBooleanData = new BooleanDataType(booleanValue, currentVariableNode.getChangeable());
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

    private InterpreterDataType MathOpNodeFunction(MathOpNode inpuMathOpNode) {
        expression(inpuMathOpNode);
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
