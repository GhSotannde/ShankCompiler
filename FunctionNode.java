import java.util.ArrayList;

public class FunctionNode extends Node {
    private String name;
    private ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    private ArrayList<VariableNode> variableArray = new ArrayList<VariableNode>();
    private ArrayList<StatementNode> statementArray = new ArrayList<StatementNode>();
    private ArrayList<InterpreterDataType> argumentArray = new ArrayList<InterpreterDataType>();

    public FunctionNode(String inputName, ArrayList<VariableNode> inputParameterArray,
    ArrayList<VariableNode> inputVariableArray, ArrayList<StatementNode> inputStatementArray) {
        name = inputName;
        parameterArray = inputParameterArray;
        variableArray = inputVariableArray;
        statementArray = inputStatementArray;
    }

    public boolean isVariadic() {
        boolean result = (name == "read" || name == "write") ? true : false;
        return result;
    }

    public String ToString() {
        String str = "Function Name: " + name + "\n\n";
        if (parameterArray != null)
            str += ToStringParameterArray();
        if (variableArray != null)
            str += ToStringVariableArray();
        if (statementArray != null)
            str += ToStringStatementArray();
        str += "\n";
        return str;
    }

    public String getName() {
        return name;
    }

    public ArrayList<InterpreterDataType> getArgumentArray() {
        return argumentArray;
    }

    public ArrayList<VariableNode> getParameterArray() {
        return parameterArray;
    }

    public ArrayList<VariableNode> getVariableArray() {
        return variableArray;
    }

    public ArrayList<StatementNode> getStatementArray() {
        return statementArray;
    }

    public void setArgumentArray(ArrayList<InterpreterDataType> inputArgumentArray) {
        argumentArray = inputArgumentArray;
        updateParameterVariables();
    }

    private String ToStringParameterArray() {
        String str = "Parameters:\n";
        for (VariableNode node : parameterArray) {
            str += "    ";
            if (node.getChangeable() == true)
                str += "var ";
            str += node.getName() + ":" + node.getType() + "\n";
        }
        str += "\n";
        return str;
    }

    private String ToStringVariableArray() {
        String str = "Variables:\n";
        for (VariableNode node : variableArray) {
            str += "    " + node.getName() + ":" + node.getType();
            if (node.getValue() != null) {
                str += " = " + node.getValue().ToString();
            }
            if (node.getHasTypeLimit() == 1) { //If == 1, node has type limit and needs to print accordingly
                if (node.getType() == VariableNode.variableType.REAL) {
                    str += " From " + node.getRealFrom() + " To " + node.getRealTo();
                }
                else {
                    str += " From " + node.getIntFrom() + " To " + node.getIntTo();
                }
            }
            str += "\n";
        }
        str += "\n";
        return str;
    }

    private String ToStringStatementArray() {
        String str = "Function Statements:\n\n";
        for (StatementNode node : statementArray) {
            str += node.ToString() + "\n";
        }
        return str;
    }

    private void updateParameterVariables() {
        for (int i = 0; i < argumentArray.size(); i++) {
            if (argumentArray.get(i) instanceof IntegerDataType) {
                IntegerDataType integerArgument = (IntegerDataType) argumentArray.get(i);
                IntegerNode integerArgumentToNode = new IntegerNode(integerArgument.getData());
                parameterArray.get(i).setValue(integerArgumentToNode);
            }
            else if (argumentArray.get(i) instanceof RealDataType) {
                RealDataType realArgument = (RealDataType) argumentArray.get(i);
                RealNode realArgumentToNode = new RealNode(realArgument.getData());
                parameterArray.get(i).setValue(realArgumentToNode);
            }
            else if (argumentArray.get(i) instanceof BooleanDataType) {
                BooleanDataType booleanArgument = (BooleanDataType) argumentArray.get(i);
                BooleanNode booleanArgumentToNode = new BooleanNode(booleanArgument.getData());
                parameterArray.get(i).setValue(booleanArgumentToNode);
            }
            else if (argumentArray.get(i) instanceof StringDataType) {
                StringDataType stringArgument = (StringDataType) argumentArray.get(i);
                StringNode stringArgumentToNode = new StringNode(stringArgument.getData());
                parameterArray.get(i).setValue(stringArgumentToNode);
            }
            else if (argumentArray.get(i) instanceof CharacterDataType) {
                CharacterDataType characterArgument = (CharacterDataType) argumentArray.get(i);
                CharacterNode characterArgumentToNode = new CharacterNode(characterArgument.getData());
                parameterArray.get(i).setValue(characterArgumentToNode);
            }
            else if (argumentArray.get(i) instanceof ArrayDataType) {
                ArrayDataType arrayArgument = (ArrayDataType) argumentArray.get(i);
                int start = arrayArgument.getStartIndex();
                int end = arrayArgument.getEndIndex();
                ArrayList<InterpreterDataType> arrayArgumentArray = arrayArgument.getData();
                VariableNode parameter = parameterArray.get(i);
                parameter.setRange(start, end);
                for (int j = 0; j < arrayArgumentArray.size(); j++) {
                    if (arrayArgumentArray.get(j) instanceof IntegerDataType) {
                        IntegerDataType integerArgument = (IntegerDataType) arrayArgumentArray.get(i);
                        IntegerNode integerArgumentToNode = new IntegerNode(integerArgument.getData());
                        parameter.setArray(j, integerArgumentToNode, VariableNode.variableType.INTEGER);
                    }
                    else if (arrayArgumentArray.get(j) instanceof RealDataType) {
                        RealDataType realArgument = (RealDataType) arrayArgumentArray.get(i);
                        RealNode realArgumentToNode = new RealNode(realArgument.getData());
                        parameter.setArray(j, realArgumentToNode, VariableNode.variableType.REAL);
                    }
                    else if (arrayArgumentArray.get(j) instanceof BooleanDataType) {
                        BooleanDataType booleanArgument = (BooleanDataType) arrayArgumentArray.get(i);
                        BooleanNode booleanArgumentToNode = new BooleanNode(booleanArgument.getData());
                        parameter.setArray(j, booleanArgumentToNode, VariableNode.variableType.BOOLEAN);
                    }
                    else if (arrayArgumentArray.get(j) instanceof StringDataType) {
                        StringDataType stringArgument = (StringDataType) arrayArgumentArray.get(i);
                        StringNode stringArgumentToNode = new StringNode(stringArgument.getData());
                        parameter.setArray(j, stringArgumentToNode, VariableNode.variableType.STRING);
                    }
                    else if (arrayArgumentArray.get(j) instanceof CharacterDataType) {
                        CharacterDataType characterArgument = (CharacterDataType) arrayArgumentArray.get(i);
                        CharacterNode characterArgumentToNode = new CharacterNode(characterArgument.getData());
                        parameter.setArray(j, characterArgumentToNode, VariableNode.variableType.CHARACTER);
                    }
                    else {
                        System.out.println("ERROR: Unknown data type found in argument array.");
                        System.exit(6);
                    }
                }
            }
            else {
                System.out.println("ERROR: Unknown data type found in function.");
                System.exit(7);
            }
        }
    }


    
}
