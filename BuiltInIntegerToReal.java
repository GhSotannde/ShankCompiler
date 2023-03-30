import java.util.ArrayList;

public class BuiltInIntegerToReal extends FunctionNode {

    public BuiltInIntegerToReal() {
        super("integertoreal", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 2 && inputData.get(0) instanceof IntegerDataType && inputData.get(1) instanceof RealDataType && inputData.get(1).isChangeable() == true) {
            IntegerDataType someIntegerData = (IntegerDataType) inputData.get(0);
            int someInteger = someIntegerData.getData();
            float someReal = someInteger * (float) 1;
            RealDataType someRealData = (RealDataType) inputData.get(1);
            someRealData.setData(someReal);
            inputData.set(1, someRealData);
        }
        else {
            System.out.println("Error: Incorrect arguments for IntegerToReal function.");
            System.exit(0);
        }
    }

}
