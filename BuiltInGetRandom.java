import java.util.ArrayList;
import java.util.Random;

public class BuiltInGetRandom extends FunctionNode {

    public BuiltInGetRandom() {
        super("GetRandom", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof IntegerDataType && inputData.get(0).isChangeable() == true) { //Checks for correct arguments
            Random rand = new Random();
            IntegerDataType resultIntegerData = (IntegerDataType) inputData.get(0);
            int resultInteger = rand.nextInt(100); //Returns random number between 0 and 99
            resultIntegerData.setData(resultInteger);
            inputData.set(0, resultIntegerData);
        }
        else {
            System.out.println("Error: Incorrect arguments for GetRandom function.");
            System.exit(0);
        }
    }

}
