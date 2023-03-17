import java.util.ArrayList;

public class BuiltInSubstring extends FunctionNode {

    private ArrayList<InterpreterDataType> arguments = new ArrayList<InterpreterDataType>();
    private StringDataType stringData = new StringDataType("Hello World", false);
    private IntegerDataType indexData = new IntegerDataType(0, false);
    private IntegerDataType intData = new IntegerDataType(5, false);
    private StringDataType returnStringData = new StringDataType("", true);

    public BuiltInSubstring() {
        super("Substring", null, null, null);
        arguments.add(stringData); 
        arguments.add(indexData);
        arguments.add(intData);
        arguments.add(returnStringData);
        execute(arguments); //Temporary to allow for testing
        System.out.println("Substring: Input String: " + stringData.getData() + ", Input Index: " + indexData.getData() + ", Input Length: " + intData.getData() + ", Output String: " + returnStringData.getData() + "\n");
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 4 && inputData.get(0) instanceof StringDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(2) instanceof IntegerDataType
        && inputData.get(3) instanceof StringDataType && inputData.get(3).isChangeable() == true) {
            StringDataType someStringData = (StringDataType) inputData.get(0);
            IntegerDataType indexData = (IntegerDataType) inputData.get(1);
            IntegerDataType lengthData = (IntegerDataType) inputData.get(2);
            StringDataType resultStringData = (StringDataType) inputData.get(3);
            int index = indexData.getData();
            int length = lengthData.getData();
            String someString = someStringData.getData();
            String resultString = "";
            for (int i = index; i < index + length; i++) { //Prints string from char at given index until char at index plus given length
                resultString += someString.charAt(i);
            }
            resultStringData.setData(resultString);
            inputData.set(3, resultStringData);
        }
        else {
            System.out.println("Error: Incorrect arguments for Substring function.");
            System.exit(0);
        }
    }

}