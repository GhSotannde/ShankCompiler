import java.util.ArrayList;

public class BuiltInRealToInteger extends FunctionNode {

    // Fills parameter array with default parameters in order to compare type and variability to future arguments
    VariableNode realParameter = new VariableNode("someReal", VariableNode.variableType.REAL, false);
    VariableNode resultIntParameter = new VariableNode("someInt", VariableNode.variableType.INTEGER, true);
    static ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    
    public BuiltInRealToInteger() {
        super("realtointeger", parameterArray, null, null, null, true);
        parameterArray.add(realParameter);
        parameterArray.add(resultIntParameter);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        // Checks for correct arguments
        if (inputData.size() == 2 && inputData.get(0) instanceof RealDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(1).isChangeable() == true) {
            RealDataType someRealData = (RealDataType) inputData.get(0);
            float someReal = someRealData.getData();
            int someInt = (int) someReal;
            IntegerDataType someIntegerData = (IntegerDataType) inputData.get(1);
            someIntegerData.setData(someInt);
            inputData.set(1, someIntegerData);
        }
        else {
            System.out.println("Error: Incorrect arguments for RealToInteger function.");
            System.exit(0);
        }
    }

}