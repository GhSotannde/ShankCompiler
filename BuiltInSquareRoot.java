import java.util.ArrayList;
import java.lang.Math;

public class BuiltInSquareRoot extends FunctionNode {

    public BuiltInSquareRoot() {
        super("squareroot", null, null, null);
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
