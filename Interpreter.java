import java.util.*;

public class Interpreter {

    public Interpreter(HashMap<String, FunctionNode> inputFunctionMap) throws SyntaxErrorException {
        Collection<FunctionNode> functionArray = inputFunctionMap.values();
        for (FunctionNode function : functionArray) {
            interpretFunction(function);
        }
    }

    private void AssignmentNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, AssignmentNode inputAssignmentNode) {
        AssignmentNode currentAssignmentNode = inputAssignmentNode;
        String assignmentTarget = currentAssignmentNode.getTarget().getName();
        Node assignmentValue = currentAssignmentNode.getValue();
        if (!(inputLocalVariableMap.get(assignmentTarget).isChangeable())) { //System exits and gives error message if user attempts to alter a constant
            System.out.println("ERROR: Cannot change the value of a constant.");
            System.exit(17);
        }
        if (assignmentValue instanceof IntegerNode) {
            IntegerNode currentIntegerNode = (IntegerNode) assignmentValue;
            IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
            inputLocalVariableMap.put(assignmentTarget, newIntegerData); //Add new Integer data to the variable map, with the assignment target as its variable name
        }
        else if (assignmentValue instanceof MathOpNode) {
            Node newNode = expression(inputLocalVariableMap, assignmentValue); //Run through expression method in order to simplify math op expression and receive a basic data type
            if (newNode instanceof IntegerNode) {
                IntegerNode currentIntNode = (IntegerNode) newNode;
                IntegerDataType newIntegerData = new IntegerDataType(currentIntNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newIntegerData);
            }
            else if (newNode instanceof RealNode) {
                RealNode currentRealNode = (RealNode) newNode;
                RealDataType newRealData = new RealDataType(currentRealNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newRealData);
            }
            else if (newNode instanceof StringNode) {
                StringNode currentStringNode = (StringNode) newNode;
                StringDataType newStringData = new StringDataType(currentStringNode.getValue(), inputLocalVariableMap.get(assignmentTarget).isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newStringData);
            }
            else {
                System.out.println("ERROR: Incorrect data type returned from math op calculation.");
                System.exit(14);
            }
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

    private BooleanDataType booleanCompareNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, BooleanCompareNode inputBooleanCompareNode) {
        Node leftChild = expression(inputLocalVariableMap, inputBooleanCompareNode.getLeftChild()); //Run both children through expression to simplify any nodes down to a basic data type
        Node rightChild = expression(inputLocalVariableMap, inputBooleanCompareNode.getRightChild());
        boolean value = false;
        if (leftChild instanceof IntegerNode) {
            IntegerNode leftIntChild = (IntegerNode) leftChild; //Children of booleanCompareNode must simplify to either an integer or a real node
            if (rightChild instanceof IntegerNode) {
                IntegerNode rightIntChild = (IntegerNode) rightChild; //Can only compare integers to integers, and reals to reals
                if (inputBooleanCompareNode.getType() == BooleanCompareNode.comparisonType.EQUAL) {
                    value = (leftIntChild.getValue() == rightIntChild.getValue()) ? true : false; //Compare both sides of booleanCompareNode using the given comparison operator
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
                    System.exit(7);
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type on right side of boolean compare."); 
                System.exit(4);
            }
        }
        else if (leftChild instanceof RealNode) {
            RealNode leftRealChild = (RealNode) leftChild;
            if (rightChild instanceof RealNode) {
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
            Node leftChild = expression(inputLocalVariableMap, currentMathOpNode.getLeftChild()); //Recursively calls expression in order to simplify nested MathOpNodes
            Node rightChild = expression(inputLocalVariableMap, currentMathOpNode.getRightChild());
            if (leftChild instanceof IntegerNode && rightChild instanceof IntegerNode) { //Both sides of MathOpNode must be of same data type
                IntegerNode leftIntChild = (IntegerNode) leftChild;
                IntegerNode rightIntChild = (IntegerNode) rightChild;
                int value = 0;
                if (currentMathOpNode.getType() == MathOpNode.operationType.ADD)
                    value = leftIntChild.getValue() + rightIntChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.DIVIDE)
                    value = leftIntChild.getValue() / rightIntChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.MOD)
                    value = leftIntChild.getValue() % rightIntChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.MULTIPLY)
                    value = leftIntChild.getValue() * rightIntChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.SUBTRACT)
                    value = leftIntChild.getValue() / rightIntChild.getValue();
                else {
                    System.out.println("ERROR: Incorrect operator given for math operation.");
                    System.exit(8);
                }
                IntegerNode newIntNode = new IntegerNode(value);
                return newIntNode;
            }
            else if (leftChild instanceof RealNode && rightChild instanceof RealNode) {
                RealNode leftRealChild = (RealNode) leftChild;
                RealNode rightRealChild = (RealNode) rightChild;
                float value = 0;
                if (currentMathOpNode.getType() == MathOpNode.operationType.ADD)
                    value = leftRealChild.getValue() + rightRealChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.DIVIDE)
                    value = leftRealChild.getValue() / rightRealChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.MOD)
                    value = leftRealChild.getValue() % rightRealChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.MULTIPLY)
                    value = leftRealChild.getValue() * rightRealChild.getValue();
                else if (currentMathOpNode.getType() == MathOpNode.operationType.SUBTRACT)
                    value = leftRealChild.getValue() / rightRealChild.getValue();
                else {
                    System.out.println("ERROR: Incorrect operator given for math operation.");
                    System.exit(9);
                }
                RealNode newRealNode = new RealNode(value);
                return newRealNode;
            }
            else if (leftChild instanceof StringNode && rightChild instanceof StringNode) {
                StringNode leftStringChild = (StringNode) leftChild;
                StringNode rightStringChild = (StringNode) rightChild;
                String value = "";
                if (currentMathOpNode.getType() == MathOpNode.operationType.ADD)
                    value = leftStringChild.getValue() + rightStringChild.getValue(); //Can only concatenate strings
                else {
                    System.out.println("ERROR: Incorrect operator given for math operation.");
                    System.exit(10);
                }
                StringNode newStringNode = new StringNode(value);
                return newStringNode;
            }
            else {
                System.out.println("ERROR: Can only perform operations on similar data types.");
                System.exit(11);
            }
        }
        else if (inputNode instanceof IntegerNode || inputNode instanceof RealNode || inputNode instanceof StringNode) {
            return inputNode;
        }
        else if (inputNode instanceof VariableReferenceNode) {
            VariableReferenceNode currentVariableReferenceNode = (VariableReferenceNode) inputNode;
            InterpreterDataType currentData = inputLocalVariableMap.get(currentVariableReferenceNode.getName());
            if (currentData == null) {
                System.out.println("ERROR: Referenced variable not declared.");
                System.exit(12);
            }
            else if (currentData instanceof IntegerDataType) { //Only Integer, Real, and String data types can be found in expressions
                IntegerDataType currentIntData = (IntegerDataType) currentData; //Extracts data from referenced variable...
                int intData = currentIntData.getData();
                IntegerNode newIntegerNode = new IntegerNode(intData);
                return newIntegerNode; //...and returns a new node containing extracted data
            }
            else if (currentData instanceof RealDataType) {
                RealDataType currentRealData = (RealDataType) currentData;
                float realData = currentRealData.getData();
                RealNode newRealNode = new RealNode(realData);
                return newRealNode;
            }
            else if (currentData instanceof StringDataType) {
                StringDataType currentStringData = (StringDataType) currentData;
                String stringData = currentStringData.getData();
                StringNode newStringNode = new StringNode(stringData);
                return newStringNode;
            }
            else {
                System.out.println("ERROR: Variable referenced within expression has incorrect data type.");
                System.exit(13);
            }
        }
        return null;
    }

    private void forNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, ForNode inputForNode) throws SyntaxErrorException {
        Node fromNode = expression(inputLocalVariableMap, inputForNode.getFrom()); //Simplifies possibly complex From node expression
        Node toNode = expression(inputLocalVariableMap, inputForNode.getTo()); //Does the same for To node
        if (!(fromNode instanceof IntegerNode && toNode instanceof IntegerNode)) { //For node From and To values must be expressed as integers
            System.out.println("ERROR: Incorrect values given from FOR loop.");
            System.exit(15);
        }
        if (!(inputLocalVariableMap.containsKey(inputForNode.getIntegerVariable().getName()))) { //Integer variable used for For loop must be previously declared
            System.out.println("ERROR: Integer variable for FOR loop not declared.");
            System.exit(16);
        }
        IntegerNode fromIntNode = (IntegerNode) fromNode;
        IntegerNode toIntNode = (IntegerNode) toNode;
        int fromValue = fromIntNode.getValue();
        int toValue = toIntNode.getValue();
        IntegerDataType fromData = new IntegerDataType(fromValue, true);
        String integerVariableName = inputForNode.getIntegerVariable().getName();
        inputLocalVariableMap.put(integerVariableName, fromData); //Stores integer variable back into variable map initialized with From value
        InterpreterDataType integerVariableData = inputLocalVariableMap.get(integerVariableName);
        IntegerDataType integerVariableIntegerData = (IntegerDataType) integerVariableData;
        while (integerVariableIntegerData.getData() != toValue) { //For loop continues, until integer value is equal to To value
            interpretBlock(inputLocalVariableMap, inputForNode.getStatements());
            integerVariableIntegerData = new IntegerDataType(integerVariableIntegerData.getData() + 1, true); //Integer variables increments by one on each iteration of loop
        }
    }

    private void ifNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, IfNode inputIfNode) throws SyntaxErrorException {
        BooleanCompareNode ifNodeConditionNode = inputIfNode.getCondition();
        BooleanDataType ifNodeCondition = booleanCompareNodeFunction(inputLocalVariableMap, ifNodeConditionNode);
        while (ifNodeCondition.getData() == false) { //Continues through linked list of if, elsif nodes until a condition is true
            inputIfNode = inputIfNode.getNextIf();
            if (inputIfNode == null || inputIfNode.getCondition() == null) //If inputIfNode == null, then no conditions were met. If condition == null, then we are at Else node
                break;
            ifNodeConditionNode = inputIfNode.getCondition();
            ifNodeCondition = booleanCompareNodeFunction(inputLocalVariableMap, ifNodeConditionNode);
        }
        if (inputIfNode != null) //Dont interpret any statements if no If conditions were met
            interpretBlock(inputLocalVariableMap, inputIfNode.getStatements());
    }

    private void interpretBlock(HashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<StatementNode> inputStatementArray) throws SyntaxErrorException {
        if (inputStatementArray != null) {
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
                    ForNode newForNode = (ForNode) inputStatementArray.get(i);
                    forNodeFunction(inputLocalVariableMap, newForNode);
                }
                else if (inputStatementArray.get(i) instanceof FunctionCallNode) {
                    
                }
                else if (inputStatementArray.get(i) instanceof IfNode) {
                    IfNode newIfNode = (IfNode) inputStatementArray.get(i);
                    ifNodeFunction(inputLocalVariableMap, newIfNode);
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
                    RepeatNode newRepeatNode = (RepeatNode) inputStatementArray.get(i);
                    RepeatNodeFunction(inputLocalVariableMap, newRepeatNode);
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
                    WhileNodeFunction(inputLocalVariableMap, newWhileNode);
                }
            }
        }
    }

    private void interpretFunction(FunctionNode inputFunctionNode) throws SyntaxErrorException {
        HashMap<String, InterpreterDataType> localVariableMap = new HashMap<String, InterpreterDataType>();
        ArrayList<VariableNode> variableArray = inputFunctionNode.getVariableArray();
        ArrayList<StatementNode> statementArray = inputFunctionNode.getStatementArray();
        if (variableArray != null) {
            for (int i = 0; i < variableArray.size(); i++) {
                VariableNode currentVariableNode = variableArray.get(i);
                String name = currentVariableNode.getName();
                if (currentVariableNode.getChangeable() == true) { //Variable
                    switch (currentVariableNode.getType()) {
                        case INTEGER:
                            IntegerDataType newIntegerData = new IntegerDataType(0, true);
                            localVariableMap.put(name, newIntegerData);
                            break;
                        case REAL:
                            RealDataType newRealData = new RealDataType(0, true);
                            localVariableMap.put(name, newRealData);
                            break;
                        case CHARACTER:
                            CharacterDataType newCharData = new CharacterDataType(' ', true);
                            localVariableMap.put(name, newCharData);
                            break;
                        case STRING:
                            StringDataType newStringData = new StringDataType(" ", true);
                            localVariableMap.put(name, newStringData);
                            break;
                        case BOOLEAN:
                            BooleanDataType newBooleanData = new BooleanDataType(false, true);
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
                else { //Constant
                    switch (currentVariableNode.getType()) {
                        case INTEGER:
                            IntegerNode newIntNode = (IntegerNode) currentVariableNode.getValue();
                            IntegerDataType newIntegerData = new IntegerDataType(newIntNode.getValue(), false);
                            localVariableMap.put(name, newIntegerData);
                            break;
                        case REAL:
                            RealNode newRealNode = (RealNode) currentVariableNode.getValue();
                            RealDataType newRealData = new RealDataType(newRealNode.getValue(), false);
                            localVariableMap.put(name, newRealData);
                            break;
                        case CHARACTER:
                            CharacterNode newCharNode = (CharacterNode) currentVariableNode.getValue();
                            CharacterDataType newCharData = new CharacterDataType(newCharNode.getValue(), false);
                            localVariableMap.put(name, newCharData);
                            break;
                        case STRING:
                            StringNode newStringNode = (StringNode) currentVariableNode.getValue();
                            StringDataType newStringData = new StringDataType(newStringNode.getValue(), false);
                            localVariableMap.put(name, newStringData);
                            break;
                        case BOOLEAN:
                            BooleanNode newBooleanNode = (BooleanNode) currentVariableNode.getValue();
                            BooleanDataType newBooleanData = new BooleanDataType(newBooleanNode.getValue(), false);
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
            }
        }
        interpretBlock(localVariableMap, statementArray);
    }

    private InterpreterDataType MathOpNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, MathOpNode inputMathOpNode) throws SyntaxErrorException {
        Node leftChildNode = expression(inputLocalVariableMap, inputMathOpNode.getLeftChild());
        Node rightChildNode = expression(inputLocalVariableMap, inputMathOpNode.getRightChild());
        if (leftChildNode instanceof StringNode && rightChildNode instanceof StringNode) { //Can only perform operations on data of the same type
            if (inputMathOpNode.getType() != MathOpNode.operationType.ADD) //Can only concatenates strings
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

    private void RepeatNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, RepeatNode inputRepeatNode) throws SyntaxErrorException {
        BooleanDataType booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputRepeatNode.getCondition());
        do { //Repeat Until loop runs once before first boolean check
            interpretBlock(inputLocalVariableMap, inputRepeatNode.getStatements());
            booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputRepeatNode.getCondition());
        } while (booleanCompare.getData() == false);
    }

    private InterpreterDataType VariableReferenceNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, VariableReferenceNode inputVariableReferenceNode) throws SyntaxErrorException {
        InterpreterDataType variableMapKeyValue = inputLocalVariableMap.get(inputVariableReferenceNode.getName());
        if (variableMapKeyValue == null) {
            System.out.println("ERROR: Referenced variable not declared.");
                System.exit(14);
        }
        return variableMapKeyValue;
    }

    private void WhileNodeFunction(HashMap<String, InterpreterDataType> inputLocalVariableMap, WhileNode inputWhileNode) throws SyntaxErrorException {
        BooleanDataType booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputWhileNode.getCondition());
        while (booleanCompare.getData() == true) {
            interpretBlock(inputLocalVariableMap, inputWhileNode.getStatements());
            booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputWhileNode.getCondition());
        }
    }
}