import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode {

    VariableNode arrayParameter = new VariableNode("someArray", 0, 0, VariableNode.variableType.INTEGER);
    VariableNode integerParameter = new VariableNode("someInteger", VariableNode.variableType.INTEGER, true);
    static ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    
    public BuiltInEnd() {
        super("end", parameterArray, null, null, null, true);
        parameterArray.add(arrayParameter);
        parameterArray.add(integerParameter);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 2 && inputData.get(0) instanceof ArrayDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(1).isChangeable() == true) {
            ArrayDataType inputArray = (ArrayDataType) inputData.get(0);
            IntegerDataType inputInteger = (IntegerDataType) inputData.get(1);
            inputInteger.setData(inputArray.getEndIndex());
            inputData.set(1, inputInteger);
        }
        else {
            System.out.println("Error: Incorrect arguments for End function.");
            System.exit(0);
        }
    }
}