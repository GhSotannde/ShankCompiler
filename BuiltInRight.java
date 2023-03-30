import java.util.ArrayList;

public class BuiltInRight extends FunctionNode {

    public BuiltInRight() {
        super("right", null, null, null);
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