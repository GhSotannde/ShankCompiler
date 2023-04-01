import java.util.ArrayList;
import java.lang.Math;

public class BuiltInSquareRoot extends FunctionNode {

    VariableNode floatParameter = new VariableNode("someFloat", VariableNode.variableType.REAL, false);
    VariableNode resultFloatParameter = new VariableNode("result", VariableNode.variableType.REAL, true);
    static ArrayList<VariableNode> parameterArray = new ArrayList<VariableNode>();
    
    public BuiltInSquareRoot() {
        super("squareroot", parameterArray, null, null, null, true);
        parameterArray.add(floatParameter);
        parameterArray.add(resultFloatParameter);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 2 && inputData.get(0) instanceof RealDataType && inputData.get(1) instanceof RealDataType && inputData.get(1).isChangeable() == true) {
            RealDataType someFloatData = (RealDataType) inputData.get(0);
            RealDataType resultData = (RealDataType) inputData.get(1);
            float someFloat = someFloatData.getData();
            float result = (float) Math.sqrt(someFloat);
            resultData.setData(result);
            inputData.set(1, resultData);
        }
        else {
            System.out.println("Error: Incorrect arguments for SquareRoot function.");
            System.exit(0);
        }
    }

}
