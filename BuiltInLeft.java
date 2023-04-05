import java.util.ArrayList;

public class BuiltInLeft extends FunctionNode {

    // Fills parameter array with default parameters in order to compare type and variability to future arguments
    VariableNode stringParameter = new VariableNode("someString", VariableNode.variableType.STRING, false);
    VariableNode intParameter = new VariableNode("length", VariableNode.variableType.INTEGER, false);
    VariableNode resultStringParameter = new VariableNode("resultString", VariableNode.variableType.STRING, true);
    static ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    
    public BuiltInLeft() {
        super("left", parameterArray, null, null, null, true);
        parameterArray.add(stringParameter);
        parameterArray.add(intParameter);
        parameterArray.add(resultStringParameter);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        // Checks for correct arguments
        if (inputData.size() == 3 && inputData.get(0) instanceof StringDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(2) instanceof StringDataType 
        && inputData.get(2).isChangeable() == true) {
            StringDataType someStringData = (StringDataType) inputData.get(0);
            StringDataType resultStringData = (StringDataType) inputData.get(2);
            IntegerDataType lengthData = (IntegerDataType) inputData.get(1);
            int length = lengthData.getData();
            String someString = someStringData.getData();
            String resultString = "";
            for (int i = 0; i < length; i++) { //Adds chars from start of node until char at indicated length
                resultString += someString.charAt(i);
            }
            resultStringData.setData(resultString);
            inputData.set(2, resultStringData);
        }
        else {
            System.out.println("Error: Incorrect arguments for Left function.");
            System.exit(0);
        }
    }

}