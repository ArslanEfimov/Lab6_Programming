package main.java.shared.io;

import main.java.shared.network.messaging.Response;

import java.util.LinkedList;
import java.util.Scanner;

public class UserIO {

    private Scanner scanner;
    private static boolean clientWork;

    private static LinkedList<String> commandsToEnter;
    private static Response response;
    public UserIO(){
        this.scanner = new Scanner(System.in, "UTF-8");
        this.clientWork = true;
        this.commandsToEnter = new LinkedList<>();
    }

    public UserIO(Scanner scanner){
        this.scanner = scanner;
    }

    public String readLine(){
        if(!scanner.hasNextLine()){
            System.exit(-39);
        }
        return scanner.nextLine();
    }

    public void print(String str){
        System.out.print(str);
    }

    public void println(String st){
        System.out.println(st);
    }

    public void printerr(String err){
        System.err.println(err+'\n');
    }
    public void printPreamble(){
        System.out.print(">");
    }

    public boolean getClientWork(){
        return clientWork;
    }

    public static void setClientWork(boolean inClientWork){
        clientWork = inClientWork;
    }
    public static LinkedList<String> getCommandsToEnter(){
        return commandsToEnter;
    }
    public static void setCommandsToEnter(LinkedList<String> commandsToEnter){
        UserIO.commandsToEnter = commandsToEnter;
    }

}
