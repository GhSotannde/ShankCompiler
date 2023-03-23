import java.util.ArrayList;
import java.util.Scanner;

public class BuiltInRead extends FunctionNode {

    public BuiltInRead() {
        super("Read", null, null, null);
    }

    public void execute(ArrayList<InterpreterDataType> inputData) {
        Scanner sc = new Scanner(System.in);
        for (InterpreterDataType arg : inputData) {
            if (arg.isChangeable() == true) { //Calls Scanner depending on data type of argument
                System.out.println("Read:\n");
                if (arg instanceof IntegerDataType) {
                    IntegerDataType intArg = (IntegerDataType) arg;
                    System.out.println("Enter Integer Value: ");
                    intArg.setData(sc.nextInt());
                    System.out.println();
                    arg = intArg;
                }
                else if (arg instanceof RealDataType) {
                    RealDataType realArg = (RealDataType) arg;
                    System.out.println("Enter Real Value: ");
                    realArg.setData(sc.nextFloat());
                    System.out.println();
                    arg = realArg;
                }
                else if (arg instanceof StringDataType) {
                    StringDataType stringArg = (StringDataType) arg;
                    System.out.println("Enter String Value: ");
                    stringArg.setData(sc.nextLine());
                    System.out.println();
                    arg = stringArg;
                }
                else if (arg instanceof CharacterDataType) {
                    CharacterDataType charArg = (CharacterDataType) arg;
                    System.out.println("Enter Char Value: ");
                    charArg.setData(sc.nextLine().charAt(0));
                    System.out.println();
                    arg = charArg;
                }
                else if (arg instanceof BooleanDataType) {
                    BooleanDataType boolArg = (BooleanDataType) arg;
                    System.out.println("Enter Boolean Value: ");
                    boolArg.setData(sc.nextBoolean());
                    System.out.println();
                    arg = boolArg;
                }
                else {
                    System.out.println("Error: Incorrect data type given as argument in Read function call.");
                    System.exit(1); 
                }
            }
            else {
                System.out.println("Error: Non-changeable argument in function call.");
                System.exit(0);
            }
        }
        sc.close();
    }

}
