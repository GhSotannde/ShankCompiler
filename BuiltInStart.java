import java.util.ArrayList;

public class BuiltInStart extends FunctionNode {

    public BuiltInStart() {
        super("Start", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof IntegerDataType && inputData.get(0).isChangeable() == true) {
            // start = first index of array
            IntegerDataType startData = (IntegerDataType) inputData.get(0);
            int start;
            startData.setData(start);
            inputData.set(0, startData);
        }
        else {
            System.out.println("Error: Incorrect arguments for Start function.");
            System.exit(0);
        }
    }

}