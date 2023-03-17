import java.util.ArrayList;

public class BuiltInRight extends FunctionNode {

    private ArrayList<InterpreterDataType> arguments = new ArrayList<InterpreterDataType>();
    private StringDataType stringData = new StringDataType("Hello World", false);
    private IntegerDataType intData = new IntegerDataType(3, false);
    private StringDataType returnStringData = new StringDataType("", true);

    public BuiltInRight() {
        super("Right", null, null, null);
        arguments.add(stringData); 
        arguments.add(intData);
        arguments.add(returnStringData);
        execute(arguments); //Temporary to allow for testing
        System.out.println("Right: Input String: " + stringData.getData() + ", Input Length " + intData.getData() + ", Output String: " + returnStringData.getData() + "\n"); 
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 3 && inputData.get(0) instanceof StringDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(2) instanceof StringDataType 
        && inputData.get(2).isChangeable() == true) {
            StringDataType someStringData = (StringDataType) inputData.get(0);
            StringDataType resultStringData = (StringDataType) inputData.get(2);
            IntegerDataType lengthData = (IntegerDataType) inputData.get(1);
            int length = lengthData.getData();
            String someString = someStringData.getData();
            String resultString = "";
            for (int i = length; i < someString.length(); i++) { //Prints string from indicated char index to end of string
                resultString += someString.charAt(i);
            }
            resultStringData.setData(resultString);
            inputData.set(2, resultStringData);
        }
        else {
            System.out.println("Error: Incorrect arguments for Right function.");
            System.exit(0);
        }
    }

}