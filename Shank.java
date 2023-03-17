import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Shank {
    public static void main(String[] args) throws IOException, SyntaxErrorException {

        if (args.length != 1) {
            System.out.println("Inappropriate number of arguments.");
            System.exit(0);
        }

        String fileName = args[0];
        
        Path myPath = Paths.get(fileName);

        List <String> lines = Files.readAllLines(myPath, StandardCharsets.UTF_8);

        Lexer lexData = new Lexer();

        lines.forEach(line -> {
            try {
                lexData.lex(line);
            } catch (SyntaxErrorException e) {
                e.printStackTrace();
            }
        });

        lexData.addLastLineDedents();

        //lexData.printLexer();

        Parser parseData = new Parser(lexData.getArray());

        ProgramNode programNode = (ProgramNode) parseData.parse();

        BuiltInWrite builtInWrite = new BuiltInWrite();
        BuiltInRead builtInRead = new BuiltInRead();
        BuiltInLeft builtInLeft = new BuiltInLeft();
        BuiltInRight builtInRight = new BuiltInRight();
        BuiltInSubstring builtInSubstring = new BuiltInSubstring();
        BuiltInSquareRoot builtInSquareRoot = new BuiltInSquareRoot();
        BuiltInGetRandom builtInGetRandom = new BuiltInGetRandom();
        BuiltInIntegerToReal builtInIntegerToReal = new BuiltInIntegerToReal();
        BuiltInRealToInteger builtInRealToInteger = new BuiltInRealToInteger();
        BuiltInStart builtInStart = new BuiltInStart();
        BuiltInEnd builtInEnd = new BuiltInEnd();

        programNode.addToFunctionMap(builtInWrite);
        programNode.addToFunctionMap(builtInRead);
        programNode.addToFunctionMap(builtInLeft);
        programNode.addToFunctionMap(builtInRight);
        programNode.addToFunctionMap(builtInSubstring);
        programNode.addToFunctionMap(builtInSquareRoot);
        programNode.addToFunctionMap(builtInGetRandom);
        programNode.addToFunctionMap(builtInIntegerToReal);
        programNode.addToFunctionMap(builtInRealToInteger);
        programNode.addToFunctionMap(builtInStart);
        programNode.addToFunctionMap(builtInEnd);

        System.out.println(programNode.ToString());
    }
}