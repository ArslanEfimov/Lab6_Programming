package main.java.client;

import main.java.shared.io.UserIO;
import main.java.shared.managers.CommandManagers.CommandsManager;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.Response;
import main.java.client.ClientConnection.ClientConnection;
import java.io.IOException;
import java.net.SocketException;
import java.util.LinkedList;

public class ClientMain {

    private static final int PORT = 2345;
    private static final String HOST_NAME = "localhost";
    public static void main(String[] args) throws InterruptedException, IOException {
        UserIO userIO = new UserIO();
        userIO.println("~~~~~~~~~Вы запустили наше приложение, чтобы ознакомиться с командами, введите команду help~~~~~~~~~~");
        CommandsManager commandsManager = new CommandsManager();
        ClientConnection clientConnection = null;
        commandsManager.fillClientMap();
        while (userIO.getClientWork()) {
            if(clientConnection==null) {
                int retryDelay = 10000;
                boolean connected = false;
                int counter = 0;
                while(!connected) {
                    try {
                        clientConnection = new ClientConnection(HOST_NAME, PORT);
                        connected = true;
                    } catch (IOException ex) {
                        if(counter>=1){
                            System.err.println("Превышено время ожидания подключения!");
                            System.exit(1);
                        }
                        userIO.printerr("Клиент не может подключится к серверу! Пытаемся установить содениение....");
                        counter+=1;
                        Thread.sleep(retryDelay);
                    }
                }

            }
                userIO.print("\nВведите команду: ");
                userIO.printPreamble();
                String str = userIO.readLine().trim();
                UserIO.getCommandsToEnter().addLast(str);
                while(UserIO.getCommandsToEnter().size()!=0) {
                    String st = UserIO.getCommandsToEnter().poll();
                    if (commandsManager.commandCheck(st.split(" ")[0])) {
                        try {
                            Request request = commandsManager.executeClient(st);
                            if (request == null) {
                                continue;
                            }
                            clientConnection.sendDataToServer(request);
                            Response response = clientConnection.getDataFromServer();
                            if (response == null) {
                                LinkedList<String> update = new LinkedList<>();
                                update.add("getId " + str.split("\\s+")[1]);
                                UserIO.setCommandsToEnter(update);
                                continue;
                            }
                            userIO.println(String.valueOf(response));
                        } catch (SocketException ex) {
                            clientConnection.tryReconnect();
                            Request request = commandsManager.executeClient(st);
                            if (request == null) {
                                continue;
                            }
                            clientConnection.sendDataToServer(request);
                            Response response = clientConnection.getDataFromServer();
                            if (response == null) {
                                LinkedList<String> update = new LinkedList<>();
                                update.add("getId " + str.split("\\s+")[1]);
                                UserIO.setCommandsToEnter(update);
                                continue;
                            }
                            userIO.println(String.valueOf(response));
                        }
                    }
                }
        }
    }
}
