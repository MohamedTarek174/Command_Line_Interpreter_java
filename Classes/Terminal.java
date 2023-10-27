package Classes;
import java.io.File;
import java.util.*;

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
//        System.out.println("arg");
        return System.getProperty("user.dir");
    }

    public void cd(String[] args){
        
    }

    public void ls(String[] args) {
            String path = pwd();
            File Cur_path = new File(path);
            File files[] = Cur_path.listFiles();
            List<String> list_names = new ArrayList<String>();
            for (File Name : files) {
                list_names.add(Name.getName());//Will add sorted alphabetically by default

            }

        if (args.length == 0) {
            for (String Names : list_names)
                System.out.println(Names);
            return;
        }
        if (args[0] == "-r") {
            System.out.println("snfnwef");
            for(int i = list_names.size()-1;i >= 0;i--)
                System.out.println(list_names.get(i));
        }else
            System.out.println("Command not found or invalid parameters are entered! ");



    }



    public void mkdir(String[] args){
        if(args.length == 0){
            System.out.println("mkdir: missing operand");
        } else {
            for(String arg : args){             // loop through all files name given in args to create them
                File dir = new File(arg);

                if(dir.exists()){
                    System.out.println("mkdir: cannot create directory '" + arg + "': File exists");
                } else {
                    boolean success = dir.mkdir();

                    if(success){
                        System.out.println("mkdir: created directory '" + arg + "'");
                    } else {
                        System.out.println("mkdir: cannot create directory '" + arg + "'");
                    }
                }
            }
        }
    }

    public void rmdir(String[] args){

        if(args.length == 0){
            System.out.println("rmdir: missing operand");
        } else {
                File dir = new File(args[0]);

                if (args[0].equals("*")) {
                    
                    try { 
                        File f = new File(System.getProperty("user.dir")); 
                        File[] files = f.listFiles();                // list all files in the directory

                        for (int i = 0; i < files.length; i++) {     // delete all files in the directory if it's empty
                            files[i].delete(); 
                        } 

                    } catch (Exception e) { 
                        System.err.println(e.getMessage()); 
                    }

                }else if(dir.exists()){
                    boolean success = dir.delete();

                    if(success){
                        System.out.println("rmdir: removed directory '" + args[0] + "'");
                    } else {
                        System.out.println("rmdir: cannot remove directory '" + args[0] + "'");
                    }

                } else {
                    System.out.println("rmdir: failed to remove '" + args[0] + "': No such file or directory");
                }

        }
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
            case "pwd"://DONE
                System.out.print("Current Directory: ");
                System.out.println(pwd());
                break;
            case "cd":
                cd(parser.getArgs());
                break;

            case "ls"://DONE
                ls(parser.getArgs());
                break;


            case "mkdir"://DONE
                mkdir(parser.getArgs());
                break;

            case "rmdir"://DONE
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
