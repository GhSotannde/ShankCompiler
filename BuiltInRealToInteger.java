import java.util.ArrayList;

public class BuiltInRealToInteger extends FunctionNode {

    private ArrayList<InterpreterDataType> arguments = new ArrayList<InterpreterDataType>();
    private RealDataType realData = new RealDataType((float) 5.5, false);
    private IntegerDataType intData = new IntegerDataType(0, true);
    

    public BuiltInRealToInteger() {
        super("RealToInteger", null, null, null);
        arguments.add(realData); 
        arguments.add(intData);
        execute(arguments);//Temporarily allows testing
        System.out.println("RealToInteger: Input: " + realData.getData() + ", Output: " + intData.getData() + "\n"); 
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        if (inputData.size() == 2 && inputData.get(0) instanceof RealDataType && inputData.get(1) instanceof IntegerDataType && inputData.get(1).isChangeable() == true) {
            RealDataType someRealData = (RealDataType) inputData.get(0);
            float someReal = someRealData.getData();
            int someInt = (int) someReal;
            IntegerDataType someIntegerData = (IntegerDataType) inputData.get(1);
            someIntegerData.setData(someInt);
            inputData.set(1, someIntegerData);
        }
        else {
            System.out.println("Error: Incorrect arguments for RealToInteger function.");
            System.exit(0);
        }
    }

}