import java.io.IOException;
import java.util.Scanner;
import Classes.Terminal;
import Classes.Parser;


public class CLI {
    public static void main(String[] args) throws IOException {
        Terminal terminal = new Terminal();
        Parser parser = new Parser();
        Scanner input = new Scanner(System.in);
        String command;
        
        while (true) {
            System.out.print("current: " + System.getProperty("user.dir") + ": ");
            command = input.nextLine();
            parser.parse(command);
            terminal.setParser(parser);

            if (parser.getCommandName().equals("exit")) {
                break;
            }
            terminal.chooseCommandAction();
        }

        input.close();
    }
    
}
