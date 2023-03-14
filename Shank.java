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

        lexData.printLexer();

        Parser parseData = new Parser(lexData.getArray());

        ProgramNode programNode = (ProgramNode) parseData.parse();

        System.out.println(programNode.ToString());
    }
}