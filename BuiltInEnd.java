import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode {

    public BuiltInEnd() {
        super("End", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof IntegerDataType && inputData.get(0).isChangeable() == true) {
            // end = last index of array
            IntegerDataType endData = (IntegerDataType) inputData.get(0);
            int end;
            endData.setData(end);
            inputData.set(0, endData);
        }
        else {
            System.out.println("Error: Incorrect arguments for End function.");
            System.exit(0);
        }
    }
}