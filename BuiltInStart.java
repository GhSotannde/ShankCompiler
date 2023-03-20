import java.util.ArrayList;

public class BuiltInStart extends FunctionNode {

    private ArrayList<InterpreterDataType> arguments = new ArrayList<InterpreterDataType>();
    private ArrayList<InterpreterDataType> data = new ArrayList<InterpreterDataType>();
    private IntegerDataType firstIndexData = new IntegerDataType(10, false);
    private IntegerDataType secondIndexData = new IntegerDataType(20, false);
    private ArrayDataType testData = new ArrayDataType(data, true);

    public BuiltInStart() {
        super("Start", null, null, null);
        data.add(firstIndexData);
        data.add(secondIndexData);
        arguments.add(testData);
        execute(arguments); //Temporary for testing
        IntegerDataType testInt = (IntegerDataType) arguments.get(0);
        String str = "Start: Input: Array[";
        for (int i = 0; i < data.size(); i++) {
            if (i == data.size() - 1)
                str += data.get(i).ToString();
            else
                str += data.get(i).ToString() + ", ";
        }
        str += "] Output: " + testInt.getData() + '\n';
        System.out.println(str);
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