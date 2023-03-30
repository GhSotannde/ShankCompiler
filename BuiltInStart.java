import java.util.ArrayList;

public class BuiltInStart extends FunctionNode {

    public BuiltInStart() {
        super("start", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof ArrayDataType && inputData.get(0).isChangeable() == true) {
            ArrayDataType inputArray = (ArrayDataType) inputData.get(0);
            IntegerDataType startIndex = new IntegerDataType(inputArray.getStartIndex(), false);
            inputData.set(0, startIndex);
        }
        else {
            System.out.println("Error: Incorrect arguments for Start function.");
            System.exit(0);
        }
    }
}