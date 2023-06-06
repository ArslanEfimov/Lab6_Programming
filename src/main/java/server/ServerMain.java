package main.java.server;

import java.io.IOException;
import java.util.Arrays;

public class ServerMain {

    public static void main(String[] args){
        ServerApplication serverApplication = new ServerApplication();
        serverApplication.run(args[0]);
    }

}
