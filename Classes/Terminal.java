package Classes;
import java.io.File;
import java.util.Scanner;

public class Terminal {
    Parser parser;


    public Terminal(){
        parser = new Parser();
    }

    public Terminal(Parser parser){
        this.parser = parser;
    }

    public void setParser(Parser parser){
        this.parser = parser;
    }

    public Parser getParser(){
        return parser;
    }

    public void echo(String arg){
        System.out.println(arg);
    }

    public String pwd(){
        return "Current Directory: " + System.getProperty("user.dir");
    }

    public void cd(String[] args){
        
    }

    public void ls(){
        
    }

    public void mkdir(String[] args){
        
    }

    public void rmdir(String[] args){
        
    }

    public void touch(String[] args){
        
    }

    public void cp(String[] args){
        
    }

    public void cat(String[] args){
        
    }

    public void wc(String[] args){
        
    }
     
    // >
    // >>

    public void history(){
        
    }

    //This method will choose the suitable command method to be called
    public void chooseCommandAction(){

        switch (parser.getCommandName()) {

            case "echo":
                echo(parser.getArgs()[0]);
                break;
            case "pwd":
                System.out.println(pwd());
                break;
            case "cd":
                cd(parser.getArgs());
                break;

            case "ls":
                ls();
                break;

            case "mkdir":
                mkdir(parser.getArgs());
                break;

            case "rmdir":
                rmdir(parser.getArgs());
                break;

            case "touch":
                touch(parser.getArgs());
                break;

            case "cp":
                cp(parser.getArgs());
                break;

            case "cat":
                cat(parser.getArgs());
                break;

            case "wc":
                wc(parser.getArgs());
                break;

            case "history":
                history();
                break;
        
            default:
                System.out.println("Command not found or invalid parameters are entered! ");
                break;
        }
    }

}
