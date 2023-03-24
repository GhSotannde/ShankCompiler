import java.util.ArrayList;

public class BuiltInEnd extends FunctionNode {

    public BuiltInEnd() {
        super("End", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof ArrayDataType && inputData.get(0).isChangeable() == true) {
            ArrayDataType inputArray = (ArrayDataType) inputData.get(0);
            ArrayList<InterpreterDataType> arrayData = inputArray.getData();
            InterpreterDataType lastIndex = arrayData.get(arrayData.size()-1);
            if (lastIndex instanceof IntegerDataType) { //Replaces input array argument with first index of array
                IntegerDataType intLastIndex = (IntegerDataType) lastIndex;
                inputData.set(0, intLastIndex);
            }
            else if (lastIndex instanceof RealDataType) {
                RealDataType realLastIndex = (RealDataType) lastIndex;
                inputData.set(0, realLastIndex);
            }
            else if (lastIndex instanceof StringDataType) {
                StringDataType stringLastIndex = (StringDataType) lastIndex;
                inputData.set(0, stringLastIndex);
            }
            else if (lastIndex instanceof CharacterDataType) {
                CharacterDataType charLastIndex = (CharacterDataType) lastIndex;
                inputData.set(0, charLastIndex);
            }
            else if (lastIndex instanceof BooleanDataType) {
                BooleanDataType boolLastIndex = (BooleanDataType) lastIndex;
                inputData.set(0, boolLastIndex);
            }
            else {
                System.out.println("Error: Incorrect data type given as argument in Start function call.");
                System.exit(1); 
            }
        }
        else {
            System.out.println("Error: Incorrect arguments for End function.");
            System.exit(0);
        }
    }
}