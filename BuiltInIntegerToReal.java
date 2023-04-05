import java.util.ArrayList;

public class BuiltInIntegerToReal extends FunctionNode {

    // Fills parameter array with default parameters in order to compare type and variability to future arguments
    VariableNode intParameter = new VariableNode("someInteger", VariableNode.variableType.INTEGER, false);
    VariableNode resultFloatParameter = new VariableNode("someReal", VariableNode.variableType.REAL, true);
    static ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    
    public BuiltInIntegerToReal() {
        super("integertoreal", parameterArray, null, null, null, true);
        parameterArray.add(intParameter);
        parameterArray.add(resultFloatParameter);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        //Checks for correct arguments
        if (inputData.size() == 2 && inputData.get(0) instanceof IntegerDataType && inputData.get(1) instanceof RealDataType && inputData.get(1).isChangeable() == true) {
            IntegerDataType someIntegerData = (IntegerDataType) inputData.get(0);
            int someInteger = someIntegerData.getData();
            float someReal = someInteger * (float) 1;
            RealDataType someRealData = (RealDataType) inputData.get(1);
            someRealData.setData(someReal);
            inputData.set(1, someRealData);
        }
        else {
            System.out.println("Error: Incorrect arguments for IntegerToReal function.");
            System.exit(0);
        }
    }

}
