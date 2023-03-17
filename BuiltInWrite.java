import java.util.ArrayList;

public class BuiltInWrite extends FunctionNode {

    public BuiltInWrite() {
        super("Write", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        for (int i = 0; i < inputData.size() ; i++) {
            if (inputData.get(i) instanceof ArrayDataType) {
                String str = "Array[";
                ArrayDataType array = (ArrayDataType) inputData.get(i);
                ArrayList<InterpreterDataType> arrayData = array.getData();
                for (int j = 0; j < arrayData.size(); j++) {
                    str += arrayData.get(j) + ", ";
                }
                str += "] ";
                System.out.println(str);
            }
            else if (inputData.get(i) instanceof IntegerDataType || inputData.get(i) instanceof RealDataType || inputData.get(i) instanceof CharacterDataType ||
            inputData.get(i) instanceof StringDataType || inputData.get(i) instanceof BooleanDataType) {
                System.out.println(inputData.get(i).ToString() + " ");
            }
            else {
                System.out.println("ERROR");
            }
        }
    }

}