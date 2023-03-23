import java.util.ArrayList;

public class BuiltInStart extends FunctionNode {

    public BuiltInStart() {
        super("Start", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 1 && inputData.get(0) instanceof ArrayDataType && inputData.get(0).isChangeable() == true) {
            ArrayDataType inputArray = (ArrayDataType) inputData.get(0);
            ArrayList<InterpreterDataType> arrayData = inputArray.getData();
            InterpreterDataType firstIndex = arrayData.get(0);
            if (firstIndex instanceof IntegerDataType) {
                IntegerDataType intFirstIndex = (IntegerDataType) firstIndex;
                inputData.set(0, intFirstIndex); //Replaces input array argument with first index of array
            }
            else if (firstIndex instanceof RealDataType) {
                RealDataType realFirstIndex = (RealDataType) firstIndex;
                inputData.set(0, realFirstIndex);
            }
            else if (firstIndex instanceof StringDataType) {
                StringDataType stringFirstIndex = (StringDataType) firstIndex;
                inputData.set(0, stringFirstIndex);
            }
            else if (firstIndex instanceof CharacterDataType) {
                CharacterDataType charFirstIndex = (CharacterDataType) firstIndex;
                inputData.set(0, charFirstIndex);
            }
            else if (firstIndex instanceof BooleanDataType) {
                BooleanDataType boolFirstIndex = (BooleanDataType) firstIndex;
                inputData.set(0, boolFirstIndex);
            }
            else {
                System.out.println("Error: Incorrect data type given as argument in Start function call.");
                System.exit(1); 
            }
        }
        else {
            System.out.println("Error: Incorrect arguments for Start function.");
            System.exit(0);
        }
    }
}