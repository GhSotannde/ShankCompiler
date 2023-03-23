import java.util.ArrayList;

public class BuiltInWrite extends FunctionNode {

    public BuiltInWrite() {
        super("Write", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        System.out.print("Write: ");
        for (int i = 0; i < inputData.size() ; i++) {
            InterpreterDataType arg = inputData.get(i);
            if (inputData.get(i) instanceof ArrayDataType) { //Searches for data type of argument and prints accordingly
                String str = "Array[";
                ArrayDataType array = (ArrayDataType) inputData.get(i);
                ArrayList<InterpreterDataType> arrayData = array.getData();
                for (int j = 0; j < arrayData.size(); j++) {
                    str += arrayData.get(j) + ", ";
                }
                str += "] ";
                System.out.println(str);
            }
            else {
                if (arg instanceof IntegerDataType) {
                    IntegerDataType intArg = (IntegerDataType) arg;
                    System.out.print(intArg.getData() + " ");
                }
                else if (arg instanceof RealDataType) {
                    RealDataType realArg = (RealDataType) arg;
                    System.out.print(realArg.getData() + " ");
                }
                else if (arg instanceof StringDataType) {
                    StringDataType stringArg = (StringDataType) arg;
                    System.out.print(stringArg.getData() + " ");
                }
                else if (arg instanceof CharacterDataType) {
                    CharacterDataType charArg = (CharacterDataType) arg;
                    System.out.print(charArg.getData() + " ");
                }
                else if (arg instanceof BooleanDataType) {
                    BooleanDataType boolArg = (BooleanDataType) arg;
                    System.out.print(boolArg.getData() + " ");
                }
                else {
                    System.out.println("Error: Incorrect data type given as argument in Write function call.");
                    System.exit(1); 
                }
            }
        }
        System.out.print("\n\n");
    }
}