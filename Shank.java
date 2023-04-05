import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Shank {
    public static void main(String[] args) throws IOException, SyntaxErrorException {

        if (args.length != 1) { //User must provide file to be read
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

        lexData.addLastLineDedents(); //Adds appropriate amount of dedents at end of lexical token list in case file ends with an indented line

        //lexData.printLexer(); //Prints out all tokens collected by lexer

        Parser parseData = new Parser(lexData.getArray());

        ProgramNode programNode = (ProgramNode) parseData.parse();

        //System.out.println(programNode.ToString()); //Prints out node tree created by parser

        SemanticAnalysis semanticAnalysis = new SemanticAnalysis();

        semanticAnalysis.CheckAssignment(programNode);

        Interpreter interpreter = new Interpreter(programNode.getFunctionMap());
    }
}