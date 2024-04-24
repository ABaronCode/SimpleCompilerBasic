import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

public class Basic {

    public static void main(String [] args) throws Exception {
        //ensure the project only contains one argument
        if(args.length == 1) {
            Lexer LinkedLexer = new Lexer (Paths.get(args[0]));//instance of lexer class
            Parser ASTParser = new Parser (LinkedLexer.tokenStored);
            System.out.println(ASTParser.parse());
        }else if (args.length < 1) {
            System.out.println("No arguments given. Please supply a single argument to be processed.");
            System.exit(0);//Unnecessary but follows directions, should I provide a legitimate error exception here to exit?
        }else {
            System.out.println("Too many arguments. Please supply only one argument.");
            System.exit(0);//^^See above comment
        }
    }
}
