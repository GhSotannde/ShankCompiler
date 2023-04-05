import java.util.ArrayList;

public class BuiltInWrite extends FunctionNode {

    public BuiltInWrite() {
        super("write", null, null, null, null, true);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        for (int i = 0; i < inputData.size() ; i++) {
            InterpreterDataType arg = inputData.get(i);
            if (inputData.get(i) instanceof ArrayDataType) { //Searches for data type of argument and prints accordingly
                ArrayDataType array = (ArrayDataType) inputData.get(i);
                String str = "Array[";
                ArrayList<InterpreterDataType> arrayData = array.getArray();
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
                    if (stringArg.getData().charAt(0) == '\\' && stringArg.getData().charAt(1) == 'n')
                        System.out.print("\n");
                    else
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
    }
}