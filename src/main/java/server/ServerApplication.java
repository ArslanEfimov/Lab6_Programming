package main.java.server;
import main.java.server.ServerConnection.ServerConnection;
import main.java.shared.managers.CollectionManager;
import main.java.shared.managers.FileManagerReader;
import main.java.shared.managers.FileManagerWriter;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerApplication {

    public static final int PORT = 2345;

    private static Logger logger = Logger.getLogger(ServerApplication.class.getName());

    public void run(String fileName){
        FileManagerReader fileManagerReader = new FileManagerReader(fileName);
        CollectionManager collectionManager = new CollectionManager(fileManagerReader);
        ServerConnection serverConnection = new ServerConnection(PORT, collectionManager, fileManagerReader);
        FileManagerWriter fileManagerWriter = new FileManagerWriter(collectionManager,fileManagerReader);
        logger.info("Server connect!");
        System.out.println("---------- Доступные команды серверу: exit - вырубить сервер, save - сохраняет коллекцию в файл ----------");
        Thread consoleThread = new Thread(()->{
            Scanner commandServer = new Scanner(System.in);
            while(true){
                String commandLine = commandServer.nextLine();
                if("exit".equals(commandLine)){
                    logger.info("Завершение работы сервера!");
                    System.exit(0);
                    break;
                }else if("save".equals(commandLine)){
                    try {
                        fileManagerWriter.writerInFile(collectionManager);
                        logger.info("Коллекция сохранена!");
                    } catch (IOException e) {
                        logger.warning("Ошибка при записи в файл!");;
                    }
                }
            }
        });
        consoleThread.start();
        try {
            serverConnection.waitConnection();
        }catch (IOException ex){
            logger.warning("Ошибка IO");
        }catch (ClassNotFoundException ex){
            logger.warning("Ошибка при сериализации данных");
        } catch (InterruptedException e) {
            logger.warning("Поток заблокирован!");
        }
    }

}
