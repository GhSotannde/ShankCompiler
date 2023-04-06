import java.util.*;

public class Interpreter {

    private LinkedHashMap<String, FunctionNode> functionMap; // Linked hash map will maintain FIFO order of functions

    public Interpreter(LinkedHashMap<String, FunctionNode> inputFunctionMap) throws SyntaxErrorException {
        functionMap = inputFunctionMap;
        Collection<FunctionNode> functionArray = functionMap.values(); // Converts hash map into list in order to iterate through functions
        for (FunctionNode function : functionArray) {
            LinkedHashMap<String, InterpreterDataType> localVariableMap = new LinkedHashMap<String, InterpreterDataType>();
            interpretFunction(localVariableMap, function);
        }
    }

    private void addConstantToVariableMap(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, VariableNode inputVariableNode) {
        String name = inputVariableNode.getName();
        switch (inputVariableNode.getType()) {
            case INTEGER:
                IntegerNode newIntNode = (IntegerNode) inputVariableNode.getValue();
                IntegerDataType newIntegerData = new IntegerDataType(newIntNode.getValue(), false); // Constants are automatically not changeable
                inputLocalVariableMap.put(name, newIntegerData);
                break;
            case REAL:
                RealNode newRealNode = (RealNode) inputVariableNode.getValue();
                RealDataType newRealData = new RealDataType(newRealNode.getValue(), false);
                inputLocalVariableMap.put(name, newRealData);
                break;
            case CHARACTER:
                CharacterNode newCharNode = (CharacterNode) inputVariableNode.getValue();
                CharacterDataType newCharData = new CharacterDataType(newCharNode.getValue(), false);
                inputLocalVariableMap.put(name, newCharData);
                break;
            case STRING:
                StringNode newStringNode = (StringNode) inputVariableNode.getValue();
                StringDataType newStringData = new StringDataType(newStringNode.getValue(), false);
                inputLocalVariableMap.put(name, newStringData);
                break;
            case BOOLEAN:
                BooleanNode newBooleanNode = (BooleanNode) inputVariableNode.getValue();
                BooleanDataType newBooleanData = new BooleanDataType(newBooleanNode.getValue(), false);
                inputLocalVariableMap.put(name, newBooleanData);
                break;
            case ARRAY:
                int startIndex = inputVariableNode.getIntFrom();
                int endIndex = inputVariableNode.getIntTo();
                ArrayDataType newArrayData = null;
                switch (inputVariableNode.getArrayType()) {
                    case INTEGER:
                        newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, startIndex, endIndex, inputVariableNode.getChangeable());
                        break;
                    case REAL:
                        newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, startIndex, endIndex, inputVariableNode.getChangeable());
                        break;
                    case CHARACTER:
                        newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, startIndex, endIndex, inputVariableNode.getChangeable());
                        break;
                    case BOOLEAN:
                        newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, startIndex, endIndex, inputVariableNode.getChangeable());
                        break;
                    case STRING:
                        newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, startIndex, endIndex, inputVariableNode.getChangeable());
                        break;
                    default:
                        System.out.println("Error: Array type not detected.");
                        System.exit(0);
                }
                inputLocalVariableMap.put(name, newArrayData);
                break;
            default:
                System.out.println("ERROR: Variable type not detected.");
                System.exit(1);
                break;
        }
    }
    
    private void addParametersToVariableMap(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<VariableNode> inputArray) {
        if (inputArray != null) {
            for (int i = 0; i < inputArray.size(); i++) {
                VariableNode currentVariableNode = inputArray.get(i);
                String name = currentVariableNode.getName();
                switch (currentVariableNode.getType()) {
                    case INTEGER:
                        if (currentVariableNode.getValue() == null) { // If parameter does not already contain a value, a default value will be assigned
                            IntegerDataType newIntegerData = new IntegerDataType(0, currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newIntegerData);
                            break;
                        }
                        else {
                            IntegerNode newIntegerNode = (IntegerNode) currentVariableNode.getValue();
                            IntegerDataType newIntegerData = new IntegerDataType(newIntegerNode.getValue(), currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newIntegerData);
                            break;
                        }
                    case REAL:
                        if (currentVariableNode.getValue() == null) {
                            RealDataType newRealData = new RealDataType(0, currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newRealData);
                            break;
                        }
                        else {
                            RealNode newRealNode = (RealNode) currentVariableNode.getValue();
                            RealDataType newRealData = new RealDataType(newRealNode.getValue(), currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newRealData);
                            break;
                        }
                    case CHARACTER:
                        if (currentVariableNode.getValue() == null) {
                            CharacterDataType newCharacterData = new CharacterDataType(' ', currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newCharacterData);
                            break;
                        }
                        else {
                            CharacterNode newCharacterNode = (CharacterNode) currentVariableNode.getValue();
                            CharacterDataType newCharacterData = new CharacterDataType(newCharacterNode.getValue(), currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newCharacterData);
                            break;
                        }
                    case STRING:
                        if (currentVariableNode.getValue() == null) {
                            StringDataType newStringData = new StringDataType("", currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newStringData);
                            break;
                        }
                        else {
                            StringNode newStringNode = (StringNode) currentVariableNode.getValue();
                            StringDataType newStringData = new StringDataType(newStringNode.getValue(), currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newStringData);
                            break;
                        }
                    case BOOLEAN:
                        if (currentVariableNode.getValue() == null) {
                            BooleanDataType newBooleanData = new BooleanDataType(false, currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newBooleanData);
                            break;
                        }
                        else {
                            BooleanNode newBooleanNode = (BooleanNode) currentVariableNode.getValue();
                            BooleanDataType newBooleanData = new BooleanDataType(newBooleanNode.getValue(), currentVariableNode.getChangeable());
                            inputLocalVariableMap.put(name, newBooleanData);
                            break;
                        }
                    case ARRAY:
                        int startIndex = currentVariableNode.getIntFrom();
                        int endIndex = currentVariableNode.getIntTo();
                        ArrayDataType newArrayData = null;
                        if (currentVariableNode.getArrayType() != null) { // If parameter array is not empty, will copy contents over to variable map
                            switch (currentVariableNode.getArrayType()) {
                                case INTEGER:
                                    newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, startIndex, endIndex, currentVariableNode.getChangeable());
                                    if (currentVariableNode.getArrayValueAtIndex(startIndex, VariableNode.variableType.INTEGER) != null) {
                                        ArrayList<InterpreterDataType> arrayDataArrayList = newArrayData.getArray();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            IntegerNode nodeAtIndex = (IntegerNode) currentVariableNode.getArrayValueAtIndex(j, VariableNode.variableType.INTEGER);
                                            IntegerDataType integerDataAtIndex = new IntegerDataType(nodeAtIndex.getValue(), currentVariableNode.getChangeable());
                                            arrayDataArrayList.set(j, integerDataAtIndex);
                                        }
                                        newArrayData.setArray(arrayDataArrayList);
                                    }
                                    break;
                                case REAL:
                                    newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, startIndex, endIndex, currentVariableNode.getChangeable());
                                    if (currentVariableNode.getArrayValueAtIndex(startIndex, VariableNode.variableType.REAL) != null) {
                                        ArrayList<InterpreterDataType> arrayDataArrayList = newArrayData.getArray();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            RealNode nodeAtIndex = (RealNode) currentVariableNode.getArrayValueAtIndex(j, VariableNode.variableType.REAL);
                                            RealDataType realDataAtIndex = new RealDataType(nodeAtIndex.getValue(), currentVariableNode.getChangeable());
                                            arrayDataArrayList.set(j, realDataAtIndex);
                                        }
                                        newArrayData.setArray(arrayDataArrayList);
                                    }
                                    break;
                                case CHARACTER:
                                    newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, startIndex, endIndex, currentVariableNode.getChangeable());
                                    if (currentVariableNode.getArrayValueAtIndex(startIndex, VariableNode.variableType.CHARACTER) != null) {
                                        ArrayList<InterpreterDataType> arrayDataArrayList = newArrayData.getArray();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            CharacterNode nodeAtIndex = (CharacterNode) currentVariableNode.getArrayValueAtIndex(j, VariableNode.variableType.CHARACTER);
                                            CharacterDataType characterDataAtIndex = new CharacterDataType(nodeAtIndex.getValue(), currentVariableNode.getChangeable());
                                            arrayDataArrayList.set(j, characterDataAtIndex);
                                        }
                                        newArrayData.setArray(arrayDataArrayList);
                                    }
                                    break;
                                case BOOLEAN:
                                    newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, startIndex, endIndex, currentVariableNode.getChangeable());
                                    if (currentVariableNode.getArrayValueAtIndex(startIndex, VariableNode.variableType.BOOLEAN) != null) {
                                        ArrayList<InterpreterDataType> arrayDataArrayList = newArrayData.getArray();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            BooleanNode nodeAtIndex = (BooleanNode) currentVariableNode.getArrayValueAtIndex(j, VariableNode.variableType.BOOLEAN);
                                            BooleanDataType booleanDataAtIndex = new BooleanDataType(nodeAtIndex.getValue(), currentVariableNode.getChangeable());
                                            arrayDataArrayList.set(j, booleanDataAtIndex);
                                        }
                                        newArrayData.setArray(arrayDataArrayList);
                                    }
                                    break;
                                case STRING:
                                    newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, startIndex, endIndex, currentVariableNode.getChangeable());
                                    if (currentVariableNode.getArrayValueAtIndex(startIndex, VariableNode.variableType.STRING) != null) {
                                        ArrayList<InterpreterDataType> arrayDataArrayList = newArrayData.getArray();
                                        for (int j = 0; j < (endIndex - startIndex); j++) {
                                            StringNode nodeAtIndex = (StringNode) currentVariableNode.getArrayValueAtIndex(j, VariableNode.variableType.STRING);
                                            StringDataType stringDataAtIndex = new StringDataType(nodeAtIndex.getValue(), currentVariableNode.getChangeable());
                                            arrayDataArrayList.set(j, stringDataAtIndex);
                                        }
                                        newArrayData.setArray(arrayDataArrayList);
                                    }
                                    break;
                                default:
                                    System.out.println("Error: Array type not detected.");
                                    System.exit(3);
                            }
                        }
                        inputLocalVariableMap.put(name, newArrayData);
                        break;
                    default:
                        System.out.println("ERROR: Variable type not detected.");
                        System.exit(4);
                        break;
                }
            }
        }
    }

    private void addVariablesAndConstantsToVariableMap(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<VariableNode> inputArray) {
        if (inputArray != null) {
            for (int i = 0; i < inputArray.size(); i++) {
                VariableNode currentVariableNode = inputArray.get(i);
                if (currentVariableNode.getChangeable() == true) { //Variable
                    addVariableToVariableMap(inputLocalVariableMap, currentVariableNode);
                }
                else { //Constant
                    addConstantToVariableMap(inputLocalVariableMap, currentVariableNode);
                }
            }
        }
    }

    private void addVariableToVariableMap(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, VariableNode inputVariableNode) {
        String name = inputVariableNode.getName();
        if (inputVariableNode.hasTypeLimit()) {
            switch (inputVariableNode.getType()) {
                case INTEGER:
                    IntegerDataType newIntegerData = new IntegerDataType(0, true, inputVariableNode.getIntFrom(), inputVariableNode.getIntTo()); // Variables declared before statements are automatically changeable
                    inputLocalVariableMap.put(name, newIntegerData);
                    break;
                case REAL:
                    RealDataType newRealData = new RealDataType(0, true, inputVariableNode.getRealFrom(), inputVariableNode.getRealTo());
                    inputLocalVariableMap.put(name, newRealData);
                    break;
                case STRING:
                    StringDataType newStringData = new StringDataType("", true, inputVariableNode.getIntFrom(), inputVariableNode.getIntTo());
                    inputLocalVariableMap.put(name, newStringData);
                    break;
                default:
                    System.out.println("ERROR: Variable type not allowed for type limit variable.");
                    System.exit(6);
                    break;
            }
        }
        else {
            switch (inputVariableNode.getType()) {
                case INTEGER:
                    IntegerDataType newIntegerData = new IntegerDataType(0, true); // Variables declared before statements are automatically changeable
                    inputLocalVariableMap.put(name, newIntegerData);
                    break;
                case REAL:
                    RealDataType newRealData = new RealDataType(0, true);
                    inputLocalVariableMap.put(name, newRealData);
                    break;
                case CHARACTER:
                    CharacterDataType newCharData = new CharacterDataType(' ', true);
                    inputLocalVariableMap.put(name, newCharData);
                    break;
                case STRING:
                    StringDataType newStringData = new StringDataType("", true);
                    inputLocalVariableMap.put(name, newStringData);
                    break;
                case BOOLEAN:
                    BooleanDataType newBooleanData = new BooleanDataType(false, true);
                    inputLocalVariableMap.put(name, newBooleanData);
                    break;
                case ARRAY:
                    int startIndex = inputVariableNode.getIntFrom();
                    int endIndex = inputVariableNode.getIntTo();
                    ArrayDataType newArrayData = null;
                    switch (inputVariableNode.getArrayType()) {
                        case INTEGER:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.INTEGER, startIndex, endIndex, inputVariableNode.getChangeable());
                            break;
                        case REAL:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.REAL, startIndex, endIndex, inputVariableNode.getChangeable());
                            break;
                        case CHARACTER:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.CHARACTER, startIndex, endIndex, inputVariableNode.getChangeable());
                            break;
                        case BOOLEAN:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.BOOLEAN, startIndex, endIndex, inputVariableNode.getChangeable());
                            break;
                        case STRING:
                            newArrayData = new ArrayDataType(ArrayDataType.arrayDataType.STRING, startIndex, endIndex, inputVariableNode.getChangeable());
                            break;
                        default:
                            System.out.println("Error: Array type not detected.");
                            System.exit(5);
                    }
                    inputLocalVariableMap.put(name, newArrayData);
                    break;
                default:
                    System.out.println("ERROR: Variable type not detected.");
                    System.exit(6);
                    break;
            }
        }
    }

    private void AssignmentNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, AssignmentNode inputAssignmentNode) throws SyntaxErrorException {
        String assignmentTarget = inputAssignmentNode.getTarget().getName();
        Node assignmentValue = inputAssignmentNode.getValue();
        InterpreterDataType assignmentVariable = inputLocalVariableMap.get(assignmentTarget);
        if (!(assignmentVariable.isChangeable())) { //System exits and gives error message if user attempts to alter a constant
            System.out.println("ERROR: Cannot change the value of a constant.");
            System.exit(7);
        }
        if (assignmentValue instanceof VariableReferenceNode) { //Replaces a variable reference with the actual variable value
            VariableReferenceNode variableReferenceNode = (VariableReferenceNode) assignmentValue;
            InterpreterDataType variableReferenceData = VariableReferenceNodeFunction(inputLocalVariableMap, variableReferenceNode);
            if (variableReferenceData instanceof IntegerDataType) {
                IntegerDataType integerData = (IntegerDataType) variableReferenceData;
                IntegerNode integerNode = new IntegerNode(integerData.getData());
                assignmentValue = integerNode;
            }
            else if (variableReferenceData instanceof StringDataType) {
                StringDataType stringData = (StringDataType) variableReferenceData;
                StringNode stringNode = new StringNode(stringData.getData());
                assignmentValue = stringNode;
            }
            else if (variableReferenceData instanceof RealDataType) {
                RealDataType realData = (RealDataType) variableReferenceData;
                RealNode realNode = new RealNode(realData.getData());
                assignmentValue = realNode;
            }
            else if (variableReferenceData instanceof BooleanDataType) {
                BooleanDataType booleanData = (BooleanDataType) variableReferenceData;
                BooleanNode booleanNode = new BooleanNode(booleanData.getData());
                assignmentValue = booleanNode;
            }
            else if (variableReferenceData instanceof CharacterDataType) {
                CharacterDataType characterData = (CharacterDataType) variableReferenceData;
                CharacterNode characterNode = new CharacterNode(characterData.getData());
                assignmentValue = characterNode;
            }
        }
        if (inputAssignmentNode.getTarget().getIndex() != null) { //Array with index value
            if (assignmentVariable instanceof ArrayDataType) {
                ArrayDataType array = (ArrayDataType) assignmentVariable;
                int arrayIndex = 0;
                //Converts any data type found in index to integer
                if (inputAssignmentNode.getTarget().getIndex() instanceof IntegerNode) {
                    IntegerNode arrayIndexNode = (IntegerNode) inputAssignmentNode.getTarget().getIndex();
                    arrayIndex = arrayIndexNode.getValue();
                }
                else if (inputAssignmentNode.getTarget().getIndex() instanceof MathOpNode) {
                    MathOpNode mathOpNodeIndex = (MathOpNode) inputAssignmentNode.getTarget().getIndex();
                    InterpreterDataType indexData = MathOpNodeFunction(inputLocalVariableMap, mathOpNodeIndex);
                    if (indexData instanceof IntegerDataType) {
                        IntegerDataType integerIndexData = (IntegerDataType) indexData;
                        arrayIndex = integerIndexData.getData();
                        IntegerNode arrayIndexNode = new IntegerNode(arrayIndex);
                        inputAssignmentNode.getTarget().setIndex(arrayIndexNode); //Replaces previous index with index converted to integer
                    }
                    else {
                        System.out.println("ERROR: Index provided for array is of an incorrect data type.");
                        System.exit(8);  
                    }
                }
                else if (inputAssignmentNode.getTarget().getIndex() instanceof VariableReferenceNode) {
                    VariableReferenceNode variableReferenceIndex = (VariableReferenceNode) inputAssignmentNode.getTarget().getIndex();
                    if (inputLocalVariableMap.containsKey(variableReferenceIndex.getName())) {
                        InterpreterDataType referencedData = inputLocalVariableMap.get(variableReferenceIndex.getName());
                        if (referencedData instanceof IntegerDataType) {
                            IntegerDataType indexData = (IntegerDataType) referencedData;
                            arrayIndex = indexData.getData();
                        }
                        else {
                            System.out.println("ERROR: Index provided for array is of an incorrect data type.");
                            System.exit(9);
                        }
                    }
                    else {
                        System.out.println("ERROR: Variable referenced in array index not found.");
                        System.exit(10);
                    }
                }
                // Must check that assignment value matches array type of assignment target  
                if (assignmentValue instanceof MathOpNode) {
                    MathOpNode mathOpNode = (MathOpNode) assignmentValue;
                    InterpreterDataType mathOpExpressionResultData = MathOpNodeFunction(inputLocalVariableMap, mathOpNode);
                    if (mathOpExpressionResultData instanceof IntegerDataType && array.getArrayType() == ArrayDataType.arrayDataType.INTEGER) {
                        IntegerDataType integerData = (IntegerDataType) mathOpExpressionResultData;
                        array.setIndex(arrayIndex, integerData); // Overwrite index value in array 
                        inputLocalVariableMap.put(assignmentTarget, array); // Update variables data in variable map
                    }
                    else if (mathOpExpressionResultData instanceof RealDataType && array.getArrayType() == ArrayDataType.arrayDataType.REAL) {
                        RealDataType realData = (RealDataType) mathOpExpressionResultData;
                        array.setIndex(arrayIndex, realData);
                        inputLocalVariableMap.put(assignmentTarget, array);
                    }
                    else if (mathOpExpressionResultData instanceof StringDataType && array.getArrayType() == ArrayDataType.arrayDataType.STRING) {
                        StringDataType stringData = (StringDataType) mathOpExpressionResultData;
                        array.setIndex(arrayIndex, stringData);
                        inputLocalVariableMap.put(assignmentTarget, array);
                    }
                    else {
                        System.out.println("ERROR: Expression assigned to index is of an incorrect data type.");
                        System.exit(11);
                    }
                }
                else if (assignmentValue instanceof IntegerNode && array.getArrayType() == ArrayDataType.arrayDataType.INTEGER) {
                    IntegerNode intAssignmentValue = (IntegerNode) assignmentValue;
                    int intValue = intAssignmentValue.getValue();
                    IntegerDataType newIntegerData = new IntegerDataType(intValue, false);
                    array.setIndex(arrayIndex, newIntegerData);
                    inputLocalVariableMap.put(assignmentTarget, array);             
                }
                else if (assignmentValue instanceof RealNode && array.getArrayType() == ArrayDataType.arrayDataType.REAL) {
                    RealNode realAssignmentValue = (RealNode) assignmentValue;
                    float realValue = realAssignmentValue.getValue();
                    RealDataType newRealData = new RealDataType(realValue, false);
                    array.setIndex(arrayIndex, newRealData);
                    inputLocalVariableMap.put(assignmentTarget, array);
                }
                else if (assignmentValue instanceof CharacterNode && array.getArrayType() == ArrayDataType.arrayDataType.CHARACTER) {
                    CharacterNode characterAssignmentValue = (CharacterNode) assignmentValue;
                    char characterValue = characterAssignmentValue.getValue();
                    CharacterDataType newCharacterData = new CharacterDataType(characterValue, false);
                    array.setIndex(arrayIndex, newCharacterData);
                    inputLocalVariableMap.put(assignmentTarget, array);
                }
                else if (assignmentValue instanceof BooleanNode && array.getArrayType() == ArrayDataType.arrayDataType.BOOLEAN) {
                    BooleanNode booleanAssignmentValue = (BooleanNode) assignmentValue;
                    boolean booleanValue = booleanAssignmentValue.getValue();
                    BooleanDataType newBooleanData = new BooleanDataType(booleanValue, false);
                    array.setIndex(arrayIndex, newBooleanData);
                    inputLocalVariableMap.put(assignmentTarget, array);
                }
                else if (assignmentValue instanceof StringNode && array.getArrayType() == ArrayDataType.arrayDataType.STRING) {
                    StringNode stringAssignmentValue = (StringNode) assignmentValue;
                    String stringValue = stringAssignmentValue.getValue();
                    StringDataType newStringData = new StringDataType(stringValue, false);
                    array.setIndex(arrayIndex, newStringData);
                    inputLocalVariableMap.put(assignmentTarget, array);
                }
                else {
                    System.out.println("ERROR: Assignment data type does not match array data type.");
                    System.exit(12);
                }
            }
            else {
                System.out.println("ERROR: Index given for non-array data type.");
                System.exit(13);
            }
        }
        else if (assignmentValue instanceof IntegerNode) {
            IntegerNode currentIntegerNode = (IntegerNode) assignmentValue;
            IntegerDataType referencedIntegerData = (IntegerDataType) assignmentVariable;
            if (referencedIntegerData.hasTypeLimit()) {
                if (currentIntegerNode.getValue() >= referencedIntegerData.getTypeLimitFrom() && currentIntegerNode.getValue() <= referencedIntegerData.getTypeLimitTo()) {
                    IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), assignmentVariable.isChangeable(), referencedIntegerData.getTypeLimitFrom(), referencedIntegerData.getTypeLimitTo());
                    inputLocalVariableMap.put(assignmentTarget, newIntegerData); //Add new Integer data to the variable map, with the assignment target as its variable name
                }
                else {
                    System.out.println("ERROR: Assignment value outside of type limit range.");
                    System.exit(13);
                }
            }
            else {
                IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), assignmentVariable.isChangeable());
                inputLocalVariableMap.put(assignmentTarget, newIntegerData); //Add new Integer data to the variable map, with the assignment target as its variable name
            }
            
        }
        else if (assignmentValue instanceof MathOpNode) {
            Node newNode = expression(inputLocalVariableMap, assignmentValue); //Run through expression method in order to simplify math op expression and receive a basic data type
            if (newNode instanceof IntegerNode) {
                IntegerNode currentIntegerNode = (IntegerNode) newNode;
                IntegerDataType referencedIntegerData = (IntegerDataType) assignmentVariable;
                if (referencedIntegerData.hasTypeLimit()) {
                    if (currentIntegerNode.getValue() >= referencedIntegerData.getTypeLimitFrom() && currentIntegerNode.getValue() <= referencedIntegerData.getTypeLimitTo()) {
                        IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), assignmentVariable.isChangeable(), referencedIntegerData.getTypeLimitFrom(), referencedIntegerData.getTypeLimitTo());
                        inputLocalVariableMap.put(assignmentTarget, newIntegerData); //Add new Integer data to the variable map, with the assignment target as its variable name
                    }
                    else {
                        System.out.println("ERROR: Assignment value outside of type limit range.");
                        System.exit(13);
                    }
                }
                else {
                    IntegerDataType newIntegerData = new IntegerDataType(currentIntegerNode.getValue(), assignmentVariable.isChangeable());
                    inputLocalVariableMap.put(assignmentTarget, newIntegerData); //Add new Integer data to the variable map, with the assignment target as its variable name
                }
            }
            else if (newNode instanceof RealNode) {
                RealNode currentRealNode = (RealNode) newNode;
                RealDataType referencedRealData = (RealDataType) assignmentVariable;
                if (referencedRealData.hasTypeLimit()) {
                    if (currentRealNode.getValue() >= referencedRealData.getTypeLimitFrom() && currentRealNode.getValue() <= referencedRealData.getTypeLimitTo()) {
                        RealDataType newRealData = new RealDataType(currentRealNode.getValue(), assignmentVariable.isChangeable(), referencedRealData.getTypeLimitFrom(), referencedRealData.getTypeLimitTo());
                        inputLocalVariableMap.put(assignmentTarget, newRealData); //Add new Real data to the variable map, with the assignment target as its variable name
                    }
                    else {
                        System.out.println("ERROR: Assignment value outside of type limit range.");
                        System.exit(13);
                    }
                }
                else {
                    RealDataType newRealData = new RealDataType(currentRealNode.getValue(), assignmentVariable.isChangeable());
                    inputLocalVariableMap.put(assignmentTarget, newRealData); //Add new Real data to the variable map, with the assignment target as its variable name
                }
            }
            else if (newNode instanceof StringNode) {
                StringNode currentStringNode = (StringNode) newNode;
                StringDataType referencedStringData = (StringDataType) assignmentVariable;
                if (referencedStringData.hasTypeLimit()) {
                    if (currentStringNode.getValue().length() >= referencedStringData.getTypeLimitFrom() && currentStringNode.getValue().length() <= referencedStringData.getTypeLimitTo()) {
                        StringDataType newStringData = new StringDataType(currentStringNode.getValue(), assignmentVariable.isChangeable(), referencedStringData.getTypeLimitFrom(), referencedStringData.getTypeLimitTo());
                        inputLocalVariableMap.put(assignmentTarget, newStringData); //Add new String data to the variable map, with the assignment target as its variable name
                    }
                    else {
                        System.out.println("ERROR: Assignment value outside of type limit range.");
                        System.exit(13);
                    }
                }
                else {
                    StringDataType newStringData = new StringDataType(currentStringNode.getValue(), assignmentVariable.isChangeable());
                    inputLocalVariableMap.put(assignmentTarget, newStringData); //Add new String data to the variable map, with the assignment target as its variable name
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type returned from math op calculation.");
                System.exit(14);
            }
        }
        else if (assignmentValue instanceof RealNode) {
            RealNode currentRealNode = (RealNode) assignmentValue;
                RealDataType referencedRealData = (RealDataType) assignmentVariable;
                if (referencedRealData.hasTypeLimit()) {
                    if (currentRealNode.getValue() >= referencedRealData.getTypeLimitFrom() && currentRealNode.getValue() <= referencedRealData.getTypeLimitTo()) {
                        RealDataType newRealData = new RealDataType(currentRealNode.getValue(), assignmentVariable.isChangeable(), referencedRealData.getTypeLimitFrom(), referencedRealData.getTypeLimitTo());
                        inputLocalVariableMap.put(assignmentTarget, newRealData); //Add new Real data to the variable map, with the assignment target as its variable name
                    }
                    else {
                        System.out.println("ERROR: Assignment value outside of type limit range.");
                        System.exit(13);
                    }
                }
                else {
                    RealDataType newRealData = new RealDataType(currentRealNode.getValue(), assignmentVariable.isChangeable());
                    inputLocalVariableMap.put(assignmentTarget, newRealData); //Add new Real data to the variable map, with the assignment target as its variable name
                }
        }
        else if (assignmentValue instanceof CharacterNode) {
            CharacterNode currentCharacterNode = (CharacterNode) assignmentValue;
            CharacterDataType newCharacterData = new CharacterDataType(currentCharacterNode.getValue(), assignmentVariable.isChangeable());
            inputLocalVariableMap.put(assignmentTarget, newCharacterData);
        }
        else if (assignmentValue instanceof StringNode) {
            StringNode currentStringNode = (StringNode) assignmentValue;
                StringDataType referencedStringData = (StringDataType) assignmentVariable;
                if (referencedStringData.hasTypeLimit()) {
                    if (currentStringNode.getValue().length() >= referencedStringData.getTypeLimitFrom() && currentStringNode.getValue().length() <= referencedStringData.getTypeLimitTo()) {
                        StringDataType newStringData = new StringDataType(currentStringNode.getValue(), assignmentVariable.isChangeable(), referencedStringData.getTypeLimitFrom(), referencedStringData.getTypeLimitTo());
                        inputLocalVariableMap.put(assignmentTarget, newStringData); //Add new String data to the variable map, with the assignment target as its variable name
                    }
                    else {
                        System.out.println("ERROR: Assignment value outside of type limit range.");
                        System.exit(13);
                    }
                }
                else {
                    StringDataType newStringData = new StringDataType(currentStringNode.getValue(), assignmentVariable.isChangeable());
                    inputLocalVariableMap.put(assignmentTarget, newStringData); //Add new String data to the variable map, with the assignment target as its variable name
                }
        }
        else if (assignmentValue instanceof BooleanNode) {
            BooleanNode currentBooleanNode = (BooleanNode) assignmentValue;
            BooleanDataType newBooleanData = new BooleanDataType(currentBooleanNode.getValue(), assignmentVariable.isChangeable());
            inputLocalVariableMap.put(assignmentTarget, newBooleanData);
        }
        else if (assignmentValue instanceof BooleanCompareNode) {
            BooleanCompareNode currentBooleanCompareNode = (BooleanCompareNode) assignmentValue;
            BooleanDataType newBooleanCompareData = booleanCompareNodeFunction(inputLocalVariableMap, currentBooleanCompareNode);
            inputLocalVariableMap.put(assignmentTarget, newBooleanCompareData);
        }
    }

    private BooleanDataType booleanCompareNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, BooleanCompareNode inputBooleanCompareNode) throws SyntaxErrorException {
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
                    System.exit(15);
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type on right side of boolean compare."); 
                System.exit(16);
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
                    System.exit(17);
                }
            }
            else {
                System.out.println("ERROR: Incorrect data type on right side of boolean compare.");
                System.exit(18);
            }
        }
        else {
            System.out.println("ERROR: Incorrect data type on left side of boolean compare.");
            System.exit(19);
        }
        BooleanDataType newBooleanData = new BooleanDataType(value, false);
        return newBooleanData;
    }

    private void checkArgumentsAndAddToArray(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<InterpreterDataType> inputFunctionCallArgumentArray, ArrayList<ParameterNode> inputFunctionCallParameterArray, ArrayList<VariableNode> inputReferencedFunctionParameterArray) throws SyntaxErrorException {
        for (int i = 0; i < inputFunctionCallParameterArray.size(); i++) { 
            ParameterNode currentFunctionCallParameterNode = inputFunctionCallParameterArray.get(i);
            VariableNode currentReferencedFunctionParameterNode = inputReferencedFunctionParameterArray.get(i);
            VariableNode.variableType variableReferencedByFunctionCallType = null;
            if ((currentReferencedFunctionParameterNode.getChangeable() == currentFunctionCallParameterNode.getChangeable()) || currentReferencedFunctionParameterNode.getType() == VariableNode.variableType.ARRAY) {
                if (currentFunctionCallParameterNode.getChangeable() == false && currentFunctionCallParameterNode.getExpression() != null) {
                    Node functionCallArgumentExpression = currentFunctionCallParameterNode.getExpression();
                    if (functionCallArgumentExpression instanceof BooleanCompareNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.BOOLEAN;
                        BooleanCompareNode booleanCompareNodeArgument = (BooleanCompareNode) functionCallArgumentExpression;
                        BooleanDataType newBooleanArgument = booleanCompareNodeFunction(inputLocalVariableMap, booleanCompareNodeArgument);
                        inputFunctionCallArgumentArray.add(newBooleanArgument);
                    }
                    else if (functionCallArgumentExpression instanceof IntegerNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.INTEGER;
                        IntegerNode integerNodeArgument = (IntegerNode) functionCallArgumentExpression;
                        IntegerDataType newIntegerArgument = new IntegerDataType(integerNodeArgument.getValue(), false);
                        inputFunctionCallArgumentArray.add(newIntegerArgument);
                    }
                    else if (functionCallArgumentExpression instanceof RealNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.REAL;
                        RealNode realNodeArgument = (RealNode) functionCallArgumentExpression;
                        RealDataType newRealArgument = new RealDataType(realNodeArgument.getValue(), false);
                        inputFunctionCallArgumentArray.add(newRealArgument);
                    }
                    else if (functionCallArgumentExpression instanceof BooleanNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.BOOLEAN;
                        BooleanNode booleanNodeArgument = (BooleanNode) functionCallArgumentExpression;
                        BooleanDataType newBooleanArgument = new BooleanDataType(booleanNodeArgument.getValue(), false);
                        inputFunctionCallArgumentArray.add(newBooleanArgument);
                    }
                    else if (functionCallArgumentExpression instanceof CharacterNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.CHARACTER;
                        CharacterNode characterNodeArgument = (CharacterNode) functionCallArgumentExpression;
                        CharacterDataType newCharacterArgument = new CharacterDataType(characterNodeArgument.getValue(), false);
                        inputFunctionCallArgumentArray.add(newCharacterArgument);
                    }
                    else if (functionCallArgumentExpression instanceof StringNode) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.STRING;
                        StringNode stringNodeArgument = (StringNode) functionCallArgumentExpression;
                        StringDataType newStringArgument = new StringDataType(stringNodeArgument.getValue(), false);
                        inputFunctionCallArgumentArray.add(newStringArgument);
                    }
                    else if (functionCallArgumentExpression instanceof VariableReferenceNode) {
                        VariableReferenceNode newVariableReferenceNode = (VariableReferenceNode) functionCallArgumentExpression;
                        InterpreterDataType referencedVariable = VariableReferenceNodeFunction(inputLocalVariableMap, newVariableReferenceNode);
                        if (referencedVariable instanceof IntegerDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.INTEGER;
                            IntegerDataType newIntArgument = (IntegerDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newIntArgument);
                        }
                        else if (referencedVariable instanceof RealDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.REAL;
                            RealDataType newRealArgument = (RealDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newRealArgument);
                        }
                        else if (referencedVariable instanceof BooleanDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.BOOLEAN;
                            BooleanDataType newBoolArgument = (BooleanDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newBoolArgument);
                        }
                        else if (referencedVariable instanceof CharacterDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.CHARACTER;
                            CharacterDataType newCharArgument = (CharacterDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newCharArgument);
                        }
                        else if (referencedVariable instanceof StringDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.STRING;
                            StringDataType newStringArgument = (StringDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newStringArgument);
                        }
                        else if (referencedVariable instanceof ArrayDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.ARRAY;
                            ArrayDataType newArrayArgument = (ArrayDataType) referencedVariable;
                            inputFunctionCallArgumentArray.add(newArrayArgument);
                        }

                    }
                    else if (functionCallArgumentExpression instanceof MathOpNode) {
                        MathOpNode mathOpNodeArgument = (MathOpNode) functionCallArgumentExpression;
                        InterpreterDataType mathOpNodeData = MathOpNodeFunction(inputLocalVariableMap, mathOpNodeArgument);
                        if (mathOpNodeData instanceof IntegerDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.INTEGER;
                            IntegerDataType newIntegerArgument = (IntegerDataType) mathOpNodeData;
                            inputFunctionCallArgumentArray.add(newIntegerArgument);
                        }
                        else if (mathOpNodeData instanceof RealDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.REAL;
                            RealDataType newRealArgument = (RealDataType) mathOpNodeData;
                            inputFunctionCallArgumentArray.add(newRealArgument);
                        }
                        else if (mathOpNodeData instanceof StringDataType) {
                            variableReferencedByFunctionCallType = VariableNode.variableType.STRING;
                            StringDataType newStringArgument = (StringDataType) mathOpNodeData;
                            inputFunctionCallArgumentArray.add(newStringArgument);
                        }
                        else {
                            System.out.println("ERROR: Unrecognized data type retrieved from expression within function call argument");
                            System.exit(20);
                        }
                    }
                    if (currentReferencedFunctionParameterNode.getType() != variableReferencedByFunctionCallType) {
                        System.out.println("ERROR: Data type of variable referenced in function call does not match data type of function parameter.");
                        System.exit(21);
                    }
                }
                else {
                    String functionCallVariableName = currentFunctionCallParameterNode.getName();
                    if (!inputLocalVariableMap.containsKey(functionCallVariableName)) {
                        System.out.println("ERROR: Variable referenced in function call not found.");
                        System.exit(22);
                    }
                    InterpreterDataType variableReferencedByFunctionCall = inputLocalVariableMap.get(functionCallVariableName);
                    if (variableReferencedByFunctionCall instanceof IntegerDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.INTEGER;
                        IntegerDataType newIntArgument = (IntegerDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newIntArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof RealDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.REAL;
                        RealDataType newRealArgument = (RealDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newRealArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof BooleanDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.BOOLEAN;
                        BooleanDataType newBoolArgument = (BooleanDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newBoolArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof CharacterDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.CHARACTER;
                        CharacterDataType newCharArgument = (CharacterDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newCharArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof StringDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.STRING;
                        StringDataType newStringArgument = (StringDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newStringArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof ArrayDataType) {
                        variableReferencedByFunctionCallType = VariableNode.variableType.ARRAY;
                        ArrayDataType newArrayArgument = (ArrayDataType) variableReferencedByFunctionCall;
                        inputFunctionCallArgumentArray.add(newArrayArgument);
                    }
                    else {
                        System.out.println("ERROR: Data type of variable referenced in function call not found.");
                        System.exit(23);
                    }
                    if (currentReferencedFunctionParameterNode.getType() != variableReferencedByFunctionCallType) {
                        System.out.println("ERROR: Data type of variable referenced in function call does not match data type of function parameter.");
                        System.exit(24);
                    }
                }
            }
            else {
                System.out.println("ERROR: Arguments do not match parameter type.");
                System.exit(25);
            }
        }
    }

    private ArrayList<InterpreterDataType> collectFunctionCallArguments(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, FunctionCallNode inputFunctionCallNode) throws SyntaxErrorException {
        ArrayList<InterpreterDataType> functionCallArgumentArray = new ArrayList<InterpreterDataType>();
        String functionName = inputFunctionCallNode.getName().toLowerCase();
        if (!functionMap.containsKey(functionName)) {
            System.out.println("ERROR: Referenced function does not exist.");
            System.exit(26);
        }
        FunctionNode referencedFunction = functionMap.get(functionName.toLowerCase());
        if (!referencedFunction.isVariadic()) {
            ArrayList<ParameterNode> functionCallParameterArray = inputFunctionCallNode.getParameterArray();
            ArrayList<VariableNode> referencedFunctionParameterArray = referencedFunction.getParameterArray();
            if (functionCallParameterArray.size() != referencedFunctionParameterArray.size()) {
                System.out.println("ERROR: Incorrect number of arguments for function call.");
                System.exit(27);
            }
            /* Following method checks each function call argument and makes sure it matches the variable data type and changeability of corresponding function parameter
               If arguments pass test, they are added to the function call argument array */ 
            checkArgumentsAndAddToArray(inputLocalVariableMap, functionCallArgumentArray, functionCallParameterArray, referencedFunctionParameterArray);
        }
        else { //Variadric functions do not require checking of argument changeability and data type.
            ArrayList<ParameterNode> functionCallParameterArray = inputFunctionCallNode.getParameterArray();
            for (int i = 0; i < functionCallParameterArray.size(); i++) { 
                ParameterNode currentFunctionCallParameterNode = functionCallParameterArray.get(i);
                if (currentFunctionCallParameterNode.getChangeable() == false && currentFunctionCallParameterNode.getExpression() != null) {
                    Node functionCallArgumentExpression = currentFunctionCallParameterNode.getExpression();
                    if (functionCallArgumentExpression instanceof BooleanCompareNode) {
                        BooleanCompareNode booleanCompareNodeArgument = (BooleanCompareNode) functionCallArgumentExpression;
                        BooleanDataType newBooleanArgument = booleanCompareNodeFunction(inputLocalVariableMap, booleanCompareNodeArgument);
                        functionCallArgumentArray.add(newBooleanArgument);
                    }
                    else if (functionCallArgumentExpression instanceof IntegerNode) {
                        IntegerNode integerNodeArgument = (IntegerNode) functionCallArgumentExpression;
                        IntegerDataType newIntegerArgument = new IntegerDataType(integerNodeArgument.getValue(), false);
                        functionCallArgumentArray.add(newIntegerArgument);
                    }
                    else if (functionCallArgumentExpression instanceof RealNode) {
                        RealNode realNodeArgument = (RealNode) functionCallArgumentExpression;
                        RealDataType newRealArgument = new RealDataType(realNodeArgument.getValue(), false);
                        functionCallArgumentArray.add(newRealArgument);
                    }
                    else if (functionCallArgumentExpression instanceof BooleanNode) {
                        BooleanNode booleanNodeArgument = (BooleanNode) functionCallArgumentExpression;
                        BooleanDataType newBooleanArgument = new BooleanDataType(booleanNodeArgument.getValue(), false);
                        functionCallArgumentArray.add(newBooleanArgument);
                    }
                    else if (functionCallArgumentExpression instanceof CharacterNode) {
                        CharacterNode characterNodeArgument = (CharacterNode) functionCallArgumentExpression;
                        CharacterDataType newCharacterArgument = new CharacterDataType(characterNodeArgument.getValue(), false);
                        functionCallArgumentArray.add(newCharacterArgument);
                    }
                    else if (functionCallArgumentExpression instanceof StringNode) {
                        StringNode stringNodeArgument = (StringNode) functionCallArgumentExpression;
                        StringDataType newStringArgument = new StringDataType(stringNodeArgument.getValue(), false);
                        functionCallArgumentArray.add(newStringArgument);
                    }
                    else if (functionCallArgumentExpression instanceof VariableReferenceNode) {
                        VariableReferenceNode newVariableReferenceNode = (VariableReferenceNode) functionCallArgumentExpression;
                        InterpreterDataType referencedVariable = VariableReferenceNodeFunction(inputLocalVariableMap, newVariableReferenceNode);
                        if (referencedVariable instanceof IntegerDataType) {
                            IntegerDataType newIntArgument = (IntegerDataType) referencedVariable;
                            functionCallArgumentArray.add(newIntArgument);
                        }
                        else if (referencedVariable instanceof RealDataType) {
                            RealDataType newRealArgument = (RealDataType) referencedVariable;
                            functionCallArgumentArray.add(newRealArgument);
                        }
                        else if (referencedVariable instanceof BooleanDataType) {
                            BooleanDataType newBoolArgument = (BooleanDataType) referencedVariable;
                            functionCallArgumentArray.add(newBoolArgument);
                        }
                        else if (referencedVariable instanceof CharacterDataType) {
                            CharacterDataType newCharArgument = (CharacterDataType) referencedVariable;
                            functionCallArgumentArray.add(newCharArgument);
                        }
                        else if (referencedVariable instanceof StringDataType) {
                            StringDataType newStringArgument = (StringDataType) referencedVariable;
                            functionCallArgumentArray.add(newStringArgument);
                        }
                        else if (referencedVariable instanceof ArrayDataType) {
                            ArrayDataType newArrayArgument = (ArrayDataType) referencedVariable;
                            functionCallArgumentArray.add(newArrayArgument);
                        }
                    }
                    else if (functionCallArgumentExpression instanceof MathOpNode) {
                        MathOpNode mathOpNodeArgument = (MathOpNode) functionCallArgumentExpression;
                        InterpreterDataType mathOpNodeData = MathOpNodeFunction(inputLocalVariableMap, mathOpNodeArgument);
                        if (mathOpNodeData instanceof IntegerDataType) {
                            IntegerDataType newIntegerArgument = (IntegerDataType) mathOpNodeData;
                            functionCallArgumentArray.add(newIntegerArgument);
                        }
                        else if (mathOpNodeData instanceof RealDataType) {
                            RealDataType newRealArgument = (RealDataType) mathOpNodeData;
                            functionCallArgumentArray.add(newRealArgument);
                        }
                        else if (mathOpNodeData instanceof StringDataType) {
                            StringDataType newStringArgument = (StringDataType) mathOpNodeData;
                            functionCallArgumentArray.add(newStringArgument);
                        }
                        else {
                            System.out.println("ERROR: Unrecognized data type retrieved from expression within function call argument");
                            System.exit(28);
                        }
                    }
                }
                else {
                    String functionCallVariableName = currentFunctionCallParameterNode.getName();
                    if (!inputLocalVariableMap.containsKey(functionCallVariableName)) {
                        System.out.println("ERROR: Variable referenced in function call not found.");
                        System.exit(29);
                    }
                    InterpreterDataType variableReferencedByFunctionCall = inputLocalVariableMap.get(functionCallVariableName);
                    if (variableReferencedByFunctionCall instanceof IntegerDataType) {
                        IntegerDataType newIntArgument = (IntegerDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newIntArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof RealDataType) {
                        RealDataType newRealArgument = (RealDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newRealArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof BooleanDataType) {
                        BooleanDataType newBoolArgument = (BooleanDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newBoolArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof CharacterDataType) {
                        CharacterDataType newCharArgument = (CharacterDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newCharArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof StringDataType) {
                        StringDataType newStringArgument = (StringDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newStringArgument);
                    }
                    else if (variableReferencedByFunctionCall instanceof ArrayDataType) {
                        ArrayDataType newArrayArgument = (ArrayDataType) variableReferencedByFunctionCall;
                        functionCallArgumentArray.add(newArrayArgument);
                    }
                    else {
                        System.out.println("ERROR: Data type of variable referenced in function call not found.");
                        System.exit(30);
                    }
                }
            }
        }
        return functionCallArgumentArray;
    }

    private void executeBuiltInFunction(FunctionCallNode inputFunctionCallNode, ArrayList<InterpreterDataType> inputFunctionCallArgumentArray) {
        FunctionNode builtInFunction = functionMap.get(inputFunctionCallNode.getName());
        if (builtInFunction instanceof BuiltInEnd) {
            BuiltInEnd builtInEndFunction = (BuiltInEnd) builtInFunction;
            builtInEndFunction.execute(inputFunctionCallArgumentArray);
            builtInEndFunction.updateArgumentVariables(inputFunctionCallArgumentArray); // Copies changed parameters back to argument variables
        }
        else if (builtInFunction instanceof BuiltInGetRandom) {
            BuiltInGetRandom builtInGetRandomFunction = (BuiltInGetRandom) builtInFunction;
            builtInGetRandomFunction.execute(inputFunctionCallArgumentArray);
            builtInGetRandomFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInIntegerToReal) {
            BuiltInIntegerToReal builtInIntegerToRealFunction = (BuiltInIntegerToReal) builtInFunction;
            builtInIntegerToRealFunction.execute(inputFunctionCallArgumentArray);
            builtInIntegerToRealFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInLeft) {
            BuiltInLeft builtInLeftFunction = (BuiltInLeft) builtInFunction;
            builtInLeftFunction.execute(inputFunctionCallArgumentArray);
            builtInLeftFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInRead) {
            BuiltInRead builtInReadFunction = (BuiltInRead) builtInFunction;
            builtInReadFunction.execute(inputFunctionCallArgumentArray);
            builtInReadFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInRealToInteger) {
            BuiltInRealToInteger builtInRealToIntegerFunction = (BuiltInRealToInteger) builtInFunction;
            builtInRealToIntegerFunction.execute(inputFunctionCallArgumentArray);
            builtInRealToIntegerFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInRight) {
            BuiltInRight builtInRightFunction = (BuiltInRight) builtInFunction;
            builtInRightFunction.execute(inputFunctionCallArgumentArray);
            builtInRightFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInSquareRoot) {
            BuiltInSquareRoot builtInSquareRootFunction = (BuiltInSquareRoot) builtInFunction;
            builtInSquareRootFunction.execute(inputFunctionCallArgumentArray);
            builtInSquareRootFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInStart) {
            BuiltInStart builtInStartFunction = (BuiltInStart) builtInFunction;
            builtInStartFunction.execute(inputFunctionCallArgumentArray);
            builtInStartFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInSubstring) {
            BuiltInSubstring builtInSubstringFunction = (BuiltInSubstring) builtInFunction;
            builtInSubstringFunction.execute(inputFunctionCallArgumentArray);
            builtInSubstringFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else if (builtInFunction instanceof BuiltInWrite) {
            BuiltInWrite builtInWriteFunction = (BuiltInWrite) builtInFunction;
            builtInWriteFunction.execute(inputFunctionCallArgumentArray);
            builtInWriteFunction.updateArgumentVariables(inputFunctionCallArgumentArray);
        }
        else {
            System.out.println("ERROR: Built-In function not found.");
            System.exit(31);
        }
    }

    private Node expression(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, Node inputNode) throws SyntaxErrorException {
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
                    value = leftIntChild.getValue() - rightIntChild.getValue();
                else {
                    System.out.println("ERROR: Incorrect operator given for math operation.");
                    System.exit(32);
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
                    value = leftRealChild.getValue() - rightRealChild.getValue();
                else {
                    System.out.println("ERROR: Incorrect operator given for math operation.");
                    System.exit(33);
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
                    System.exit(34);
                }
                StringNode newStringNode = new StringNode(value);
                return newStringNode;
            }
            else {
                System.out.println("ERROR: Can only perform operations on similar data types.");
                System.exit(35);
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
                System.exit(36);
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
            else if (currentData instanceof ArrayDataType) { //For array index references
                ArrayDataType referencedArray = (ArrayDataType) currentData;
                Node index = currentVariableReferenceNode.getIndex();
                int arrayIndex = 0;
                if (index == null) {
                    System.out.println("ERROR: Cannot use array in expression.");
                    System.exit(37);
                }
                else {
                    if (index instanceof IntegerNode) { //First convert the index into an integer
                        IntegerNode intIndex = (IntegerNode) index;
                        arrayIndex = intIndex.getValue();
                    }
                    else if (index instanceof MathOpNode) {
                        MathOpNode mathOpIndex = (MathOpNode) index;
                        InterpreterDataType mathOpIndexData = MathOpNodeFunction(inputLocalVariableMap, mathOpIndex);
                        if (mathOpIndexData instanceof IntegerDataType) {
                            IntegerDataType intIndex = (IntegerDataType) mathOpIndexData;
                            arrayIndex = intIndex.getData();
                        }
                        else {
                            System.out.println("ERROR: Incorrect data type given for array index.");
                            System.exit(37);
                        }
                    }
                    else if (index instanceof VariableReferenceNode) {
                        VariableReferenceNode variableIndex = (VariableReferenceNode) index;
                        InterpreterDataType variableIndexData = VariableReferenceNodeFunction(inputLocalVariableMap, variableIndex);
                        if (variableIndexData instanceof IntegerDataType) {
                            IntegerDataType intIndex = (IntegerDataType) variableIndexData;
                            arrayIndex = intIndex.getData();
                        }
                        else {
                            System.out.println("ERROR: Incorrect data type given for array index.");
                            System.exit(37);
                        }
                    }
                    else {
                        System.out.println("ERROR: Incorrect data type given for array index.");
                        System.exit(37);
                    }
                }
                InterpreterDataType dataAtIndex = referencedArray.getDataAtIndex(arrayIndex);
                if (dataAtIndex == null) {
                    System.out.println("ERROR: Referenced variable not declared.");
                    System.exit(36);
                }
                else if (dataAtIndex instanceof IntegerDataType) { //Only Integer, Real, and String data types can be found in expressions
                    IntegerDataType currentIntData = (IntegerDataType) dataAtIndex; //Extracts data from referenced variable...
                    int intData = currentIntData.getData();
                    IntegerNode newIntegerNode = new IntegerNode(intData);
                    return newIntegerNode; //...and returns a new node containing extracted data
                }
                else if (dataAtIndex instanceof RealDataType) {
                    RealDataType currentRealData = (RealDataType) dataAtIndex;
                    float realData = currentRealData.getData();
                    RealNode newRealNode = new RealNode(realData);
                    return newRealNode;
                }
                else if (dataAtIndex instanceof StringDataType) {
                    StringDataType currentStringData = (StringDataType) dataAtIndex;
                    String stringData = currentStringData.getData();
                    StringNode newStringNode = new StringNode(stringData);
                    return newStringNode;
                }
            }
            else {
                System.out.println("ERROR: Variable referenced within expression has incorrect data type.");
                System.exit(37);
            }
        }
        return null;
    }

    private void forNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, ForNode inputForNode) throws SyntaxErrorException {
        Node fromNode = expression(inputLocalVariableMap, inputForNode.getFrom()); //Simplifies possibly complex From node expression
        Node toNode = expression(inputLocalVariableMap, inputForNode.getTo()); //Does the same for To node
        if (!(fromNode instanceof IntegerNode && toNode instanceof IntegerNode)) { //For node From and To values must be expressed as integers
            System.out.println("ERROR: Incorrect values given from FOR loop.");
            System.exit(38);
        }
        if (!(inputLocalVariableMap.containsKey(inputForNode.getIntegerVariable().getName()))) { //Integer variable used for For loop must be previously declared
            System.out.println("ERROR: Integer variable for FOR loop not declared.");
            System.exit(39);
        }
        IntegerNode fromIntNode = (IntegerNode) fromNode;
        IntegerNode toIntNode = (IntegerNode) toNode;
        int fromValue = fromIntNode.getValue();
        int toValue = toIntNode.getValue();
        if (fromValue > toValue) {
            System.out.println("ERROR: Starting index given for FOR loop is greater than ending index.");
            System.exit(40);
        }
        IntegerDataType fromData = new IntegerDataType(fromValue, true);
        String integerVariableName = inputForNode.getIntegerVariable().getName();
        inputLocalVariableMap.put(integerVariableName, fromData); //Stores integer variable back into variable map initialized with From value
        InterpreterDataType integerVariableData = inputLocalVariableMap.get(integerVariableName);
        IntegerDataType integerVariableIntegerData = (IntegerDataType) integerVariableData;
        while (integerVariableIntegerData.getData() <= toValue) { //For loop continues, until integer value is equal to To value
            interpretBlock(inputLocalVariableMap, inputForNode.getStatements());
            integerVariableIntegerData.setData(integerVariableIntegerData.getData()+1); //Integer variables increments by one on each iteration of loop
        }
    }

    private void functionCallNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, FunctionCallNode inputFunctionCallNode) throws SyntaxErrorException {
        ArrayList<InterpreterDataType> functionCallArgumentArray = new ArrayList<InterpreterDataType>();
        functionCallArgumentArray = collectFunctionCallArguments(inputLocalVariableMap, inputFunctionCallNode);
        functionMap.get(inputFunctionCallNode.getName()).setArgumentArray(functionCallArgumentArray);
        if (functionMap.get(inputFunctionCallNode.getName()).isBuiltIn()) {
            executeBuiltInFunction(inputFunctionCallNode, functionCallArgumentArray);
        }
        else {
            interpretFunction(inputLocalVariableMap, functionMap.get(inputFunctionCallNode.getName()));
        }
    }

    private void ifNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, IfNode inputIfNode) throws SyntaxErrorException {
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

    private void interpretBlock(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, ArrayList<StatementNode> inputStatementArray) throws SyntaxErrorException {
        if (inputStatementArray != null) {
            for (int i = 0; i < inputStatementArray.size(); i++) {
                if (inputStatementArray.get(i) instanceof AssignmentNode) {
                    AssignmentNode newAssignmentNode = (AssignmentNode) inputStatementArray.get(i);
                    AssignmentNodeFunction(inputLocalVariableMap, newAssignmentNode);
                }
                else if (inputStatementArray.get(i) instanceof ForNode) {
                    ForNode newForNode = (ForNode) inputStatementArray.get(i);
                    forNodeFunction(inputLocalVariableMap, newForNode);
                }
                else if (inputStatementArray.get(i) instanceof FunctionCallNode) {
                    FunctionCallNode newFunctionCallNode = (FunctionCallNode) inputStatementArray.get(i);
                    functionCallNodeFunction(inputLocalVariableMap, newFunctionCallNode);
                }
                else if (inputStatementArray.get(i) instanceof IfNode) {
                    IfNode newIfNode = (IfNode) inputStatementArray.get(i);
                    ifNodeFunction(inputLocalVariableMap, newIfNode);
                }
                else if (inputStatementArray.get(i) instanceof RepeatNode) {
                    RepeatNode newRepeatNode = (RepeatNode) inputStatementArray.get(i);
                    RepeatNodeFunction(inputLocalVariableMap, newRepeatNode);
                }
                else if (inputStatementArray.get(i) instanceof WhileNode) {
                    WhileNode newWhileNode = (WhileNode) inputStatementArray.get(i);
                    WhileNodeFunction(inputLocalVariableMap, newWhileNode);
                }
            }
        }
    }

    private void interpretFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, FunctionNode inputFunctionNode) throws SyntaxErrorException {
        ArrayList<VariableNode> parameterArray = inputFunctionNode.getParameterArray();
        ArrayList<VariableNode> variableArray = inputFunctionNode.getVariableArray();
        ArrayList<StatementNode> statementArray = inputFunctionNode.getStatementArray();
        addParametersToVariableMap(inputLocalVariableMap, parameterArray);
        addVariablesAndConstantsToVariableMap(inputLocalVariableMap, variableArray);
        interpretBlock(inputLocalVariableMap, statementArray);
        if (parameterArray != null) {
            ArrayList<InterpreterDataType> newArgumentArray = new ArrayList<InterpreterDataType>();
            for (int i = 0; i < parameterArray.size(); i++) {
                InterpreterDataType currentVariableData = inputLocalVariableMap.get(parameterArray.get(i).getName());
                newArgumentArray.add(currentVariableData);
            }
            inputFunctionNode.updateArgumentVariables(newArgumentArray);
        }
    }

    private InterpreterDataType MathOpNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, MathOpNode inputMathOpNode) throws SyntaxErrorException {
        Node leftChildNode = expression(inputLocalVariableMap, inputMathOpNode.getLeftChild());
        Node rightChildNode = expression(inputLocalVariableMap, inputMathOpNode.getRightChild());
        if (leftChildNode instanceof StringNode && rightChildNode instanceof StringNode) { //Can only perform operations on data of the same type
            if (inputMathOpNode.getType() != MathOpNode.operationType.ADD) //Can only concatenates strings
                throw new SyntaxErrorException(null);
            StringNode leftStringNode = (StringNode) leftChildNode;
            StringNode rightStringNode = (StringNode) rightChildNode;
            String resultString = leftStringNode.getValue() + rightStringNode.getValue();
            StringDataType newStringData = new StringDataType(resultString, false);
            return newStringData;
        }
        else if (leftChildNode instanceof RealNode && rightChildNode instanceof RealNode) {
            RealNode leftRealNode = (RealNode) leftChildNode;
            float leftValue = leftRealNode.getValue();
            RealNode rightRealNode = (RealNode) rightChildNode;
            float rightValue = rightRealNode.getValue();
            float resultValue = 0;
            switch (inputMathOpNode.getType()) {
                case ADD :
                    resultValue = leftValue + rightValue;
                    break;
                case SUBTRACT :
                    resultValue = leftValue - rightValue;
                    break;
                case MULTIPLY :
                    resultValue = leftValue * rightValue;
                    break;
                case DIVIDE :
                    resultValue = leftValue / rightValue;
                    break;
                case MOD :
                    resultValue = leftValue % rightValue;
                    break;
                default : 
                    System.out.println("ERROR: Unrecognized operator given for expression.");
                    System.exit(41);
            }
            RealDataType newRealData = new RealDataType(resultValue, false);
            return newRealData;
        }
        else if (leftChildNode instanceof IntegerNode && rightChildNode instanceof IntegerNode) {
            IntegerNode leftIntegerNode = (IntegerNode) leftChildNode;
            int leftValue = leftIntegerNode.getValue();
            IntegerNode rightIntegerNode = (IntegerNode) rightChildNode;
            int rightValue = rightIntegerNode.getValue();
            int resultValue = 0;
            switch (inputMathOpNode.getType()) {
                case ADD :
                    resultValue = leftValue + rightValue;
                    break;
                case SUBTRACT :
                    resultValue = leftValue - rightValue;
                    break;
                case MULTIPLY :
                    resultValue = leftValue * rightValue;
                    break;
                case DIVIDE :
                    resultValue = leftValue / rightValue;
                    break;
                case MOD :
                    resultValue = leftValue % rightValue;
                    break;
                default : 
                    System.out.println("ERROR: Unrecognized operator given for expression.");
                    System.exit(42);
            }
            IntegerDataType newIntegerData = new IntegerDataType(resultValue, false);
            return newIntegerData;
        }
        else
            return null;
    }

    private void RepeatNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, RepeatNode inputRepeatNode) throws SyntaxErrorException {
        BooleanDataType booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputRepeatNode.getCondition());
        do { //Repeat Until loop runs once before first boolean check
            interpretBlock(inputLocalVariableMap, inputRepeatNode.getStatements());
            booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputRepeatNode.getCondition());
        } while (booleanCompare.getData() == false);
    }

    private InterpreterDataType VariableReferenceNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, VariableReferenceNode inputVariableReferenceNode) throws SyntaxErrorException {
        if (inputVariableReferenceNode.getIndex() != null) {
            Node arrayIndex = inputVariableReferenceNode.getIndex();
            String variableName = inputVariableReferenceNode.getName();
            InterpreterDataType arrayData = inputLocalVariableMap.get(variableName);
            if (arrayData instanceof ArrayDataType) {
                ArrayDataType array = (ArrayDataType) arrayData;
                if (arrayIndex instanceof IntegerNode) {
                    IntegerNode integerNode = (IntegerNode) arrayIndex;
                    InterpreterDataType valueAtIndex = array.getDataAtIndex(integerNode.getValue());
                    return valueAtIndex;
                }
                else if (arrayIndex instanceof MathOpNode) {
                    MathOpNode mathOpNode = (MathOpNode) arrayIndex;
                    InterpreterDataType newData = MathOpNodeFunction(inputLocalVariableMap, mathOpNode);
                    if (newData instanceof IntegerDataType) {
                        IntegerDataType newIntData = (IntegerDataType) newData;
                        InterpreterDataType valueAtIndex = array.getDataAtIndex(newIntData.getData());
                        return valueAtIndex;
                    }
                    else {
                        System.out.println("ERROR: Reference node index data type unrecognized.");
                        System.exit(43);
                    }
                }
                else if (arrayIndex instanceof VariableReferenceNode) {
                    VariableReferenceNode variableReferenceNode = (VariableReferenceNode) arrayIndex;
                    InterpreterDataType newData = VariableReferenceNodeFunction(inputLocalVariableMap, variableReferenceNode);
                    if (newData instanceof IntegerDataType) {
                        IntegerDataType newIntData = (IntegerDataType) newData;
                        InterpreterDataType valueAtIndex = array.getDataAtIndex(newIntData.getData());
                        return valueAtIndex;
                    }
                    else {
                        System.out.println("ERROR: Reference node index data type unrecognized.");
                        System.exit(44);
                    }
                }
                else {
                    System.out.println("ERROR: Reference node index data type unrecognized.");
                    System.exit(45);
                }
            }
            else {
                System.out.println("ERROR: Index assigned to non-array data type.");
                System.exit(46);
            }
            
            return null;
        }
        else {
            InterpreterDataType variableMapKeyValue = inputLocalVariableMap.get(inputVariableReferenceNode.getName());
            if (variableMapKeyValue == null) {
                System.out.println("ERROR: Referenced variable not declared.");
                System.exit(47);
            }
            return variableMapKeyValue;
        }
    }

    private void WhileNodeFunction(LinkedHashMap<String, InterpreterDataType> inputLocalVariableMap, WhileNode inputWhileNode) throws SyntaxErrorException {
        BooleanDataType booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputWhileNode.getCondition());
        while (booleanCompare.getData() == true) {
            interpretBlock(inputLocalVariableMap, inputWhileNode.getStatements());
            booleanCompare = booleanCompareNodeFunction(inputLocalVariableMap, inputWhileNode.getCondition());
        }
    }
}