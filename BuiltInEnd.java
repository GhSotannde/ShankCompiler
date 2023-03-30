import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode {

    public BuiltInEnd() {
        super("end", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof ArrayDataType && inputData.get(0).isChangeable() == true) {
            ArrayDataType inputArray = (ArrayDataType) inputData.get(0);
            IntegerDataType endIndex = new IntegerDataType(inputArray.getEndIndex(), false);
            inputData.set(0, endIndex);
        }
        else {
            System.out.println("Error: Incorrect arguments for Start function.");
            System.exit(0);
        }
    }
}