package Classes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.Scanner;


public class Terminal {
    Parser parser;
    static List<String> historyList = new ArrayList<String>();


    public Terminal(){
        parser = new Parser();
    }

    public Terminal(Parser parser){
        this.parser = parser;
        historyList.add(parser.getCommandName());       // add the command to the history list
    }

    public void setParser(Parser parser){
        this.parser = parser;
        historyList.add(parser.getCommandName());       // add the command to the history list
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

    public void cd(String[] args) {
        if (args.length == 0) {
            String homeDir = System.getProperty("user.home");
            System.setProperty("user.dir", homeDir);
        } else if (args.length == 1) { //Used to make another else with no condition (Will be the path)
            if (args[0].equals("..")) {
                File curDir = new File(System.getProperty("user.dir"));
                File parentDir = curDir.getParentFile();
                if (parentDir != null && parentDir.isDirectory()) {
                    System.setProperty("user.dir", parentDir.getAbsolutePath());//change the default path to the parent one
                } else {
                    System.out.println("Something went wrong , Can't return to the prev directory.");
                }
            }else {//For the Path
                String newDirectoryPath = new String();
                for (String arg : args)
                    newDirectoryPath = arg + " ";

                File newDir = new File(newDirectoryPath);

                if (newDir.isAbsolute()) {
                    if (newDir.isDirectory()) {
                        System.setProperty("user.dir", newDir.getAbsolutePath());
                    } else {
                        System.out.println("The specified path "+newDir.getAbsolutePath()+" is not a directory.");
                    }
                } else {
                    File currentDirectory = new File(System.getProperty("user.dir"));
                    File newDirectoryFile = new File(currentDirectory, newDirectoryPath);

                    if (newDirectoryFile.isDirectory()) {
                        System.setProperty("user.dir", newDirectoryFile.getAbsolutePath());
                    }
                    else {
                        System.err.println("The specified path is not a directory.");
                    }
                }
            }
        } else {//More than one arg
            for(String ar : args)
                System.out.println(ar);
            System.err.println("Command not found or invalid parameters are entered! ");
        }
    }

    public List<String> ls(String[] args) {
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

        }
        else if (args.length > 0 && args[0].equals("-r")) {
            for(int i = list_names.size()-1;i >= 0;i--)
                System.out.println(list_names.get(i));
        }
//        else
//            System.err.println("Command not found or invalid parameters are entered! ");

        return list_names;
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

    public void touch(String[] args) throws IOException {

        if(args.length == 0) {
            System.out.println("touch: missing operand");
        }
        else if (args.length==1) {
            File file = new File(args[0]);
            if(file.createNewFile()){
                System.out.println(args[0]+" File Created");
            }else
                System.out.println("File "+args[0]+" already exists");
        }
        else{
            System.out.println("Command not found or invalid parameters are entered! ");
        }
    }

    public void rm(String[] args){

        if(args.length==0){
            System.out.println("rm: missing operand");
        }
        else{
            if(ls(parser.getArgs()).contains(args[0])){
                File file = new File(args[0]);
                System.out.println(file.getName()+" Deleted");
                file.delete();
            }
            else{
                System.out.println("rm: can't remove " + '\'' + args[0] + '\'' + " : No such file or directory");
            }

        }

    }

    public void cp(String[] args) throws IOException {

        if(args.length==0 || args.length==1){
            System.out.println("cp: missing operand");
        }
        else{
            FileInputStream src = new FileInputStream(args[0]);
            FileOutputStream dest = new FileOutputStream(args[1]);

            int i;

            while ((i = src.read()) != -1) {
                dest.write(i);
            }

                src.close();
                dest.close();

        }








    }

    public void cat(String[] args){
        if (args.length == 1) {

            try{
                File file = new File(args[0]);          // get the file & scan it
                Scanner scan = new Scanner(file);

            while (scan.hasNextLine()){                 // print the file line by line
                System.out.println(scan.nextLine());
            }   
            scan.close();

        }catch (Exception e) {                          // if the file doesn't exist
            System.out.println("Can't open file "+args[0]+" : No such file or directory ");
            }   
            
        }


        else if (args.length == 2) {
        try{
                File file1 = new File(args[0]);         // get the first file & scan it
                Scanner scan1 = new Scanner(file1); 
                
                while (scan1.hasNextLine()){            // print the first file line by line
                    System.out.println(scan1.nextLine());
                }  

                File file2 = new File(args[1]);         // get the second file & scan it
                Scanner scan2 = new Scanner(file2);

                while (scan2.hasNextLine()){            // print the second file line by line
                    System.out.println(scan2.nextLine());
                }
                scan1.close();
                scan2.close();

        }catch (Exception e) {                          // if any file doesn't exist
            System.out.println("Can't open file " +args[0]+ " or " + args[1] + " : No such file or directory ");
            } 
    }

    else{
        System.out.println("nvalid parameters are entered! ");
    }
}

    public void wc(String[] args){
        if (args.length == 1) {
            
            try{
                int lines = 0;
                int words = 0;
                int chars = 0;

                File file = new File(args[0]);          // get the file & scan it
                Scanner scan = new Scanner(file);

            while (scan.hasNextLine()){                 
                String line = scan.nextLine();          // get the line
                lines++;                                // add the current line
                chars += line.length();                 // the length of the line is the number of chars
                String[] wordsArray = line.split(" ");  // split the line into words
                words += wordsArray.length;             // count the words
            }   
            System.out.println(lines + " " + words + " " + chars + " " + args[0]);
            scan.close();

        }catch (Exception e) {                          // if the file doesn't exist
            System.out.println("Can't open file "+args[0]+" : No such file or directory ");
            }   
            
        }else{                                          // if the user entered more than one file
            System.out.println("Invalid parameters are entered! ");
        }
    }

    // >
    // >>

    public void history(){

        for (int i = 0; i < historyList.size(); i++) {              // print the history list
            System.out.println(i + 1 + "  " + historyList.get(i));
        }

    }

    //This method will choose the suitable command method to be called
    public void chooseCommandAction() throws IOException {

        switch (parser.getCommandName()) {

            case "echo"://Done
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

            case "touch"://DONE
                touch(parser.getArgs());
                break;

            case "rm"://DONE
                rm(parser.getArgs());
                break;


            case "cp"://Done
                cp(parser.getArgs());
                break;

            case "cat"://Done
                cat(parser.getArgs());
                break;

            case "wc"://Done
                wc(parser.getArgs());
                break;

            case "history"://Done
                history();
                break;

            default:
                System.err.println("Command not found or invalid parameters are entered! ");
                break;
        }
    }

}
