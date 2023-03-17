import java.util.ArrayList;
import java.lang.Math;

public class BuiltInSquareRoot extends FunctionNode {

    private ArrayList<InterpreterDataType> arguments = new ArrayList<InterpreterDataType>();
    private RealDataType realData = new RealDataType(16, false);
    private RealDataType resultData = new RealDataType(0, true);

    public BuiltInSquareRoot() {
        super("SquareRoot", null, null, null);
        arguments.add(realData); 
        arguments.add(resultData);
        execute(arguments); //Temporary to allow for testing
        System.out.println("SquareRoot: Input: " + realData.getData() + ", Output: " + resultData.getData() + "\n");
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
