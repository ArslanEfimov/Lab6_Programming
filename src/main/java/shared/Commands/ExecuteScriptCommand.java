package main.java.shared.Commands;

import main.java.shared.core.Exceptions.ScriptRecursionException;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.io.UserIO;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

public class ExecuteScriptCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private final HashSet<File> fileNames = new HashSet<>();
    private UserIO userIO;
    private LinkedList<String> commandToExecuteScript;
    public ExecuteScriptCommand(CollectionManager collectionManager) {
        super("execute_script");
        this.collectionManager = collectionManager;
        this.userIO = new UserIO();
        this.commandToExecuteScript = new LinkedList<>();
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)) {
            String path = args[0];
            File file = new File(path).getAbsoluteFile();
            try {
                if(fileNames.contains(file)){
                    throw new ScriptRecursionException();
                }
                if (file.length() == 0) {
                    userIO.println("Файл пуст или его не существует");
                }
                else if(file.canRead()){
                    fileNames.add(file);
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                    String commandLine = bufferedReader.readLine();
                    while (commandLine != null) {
                        if(commandLine.isEmpty()){
                            commandLine = bufferedReader.readLine();
                            continue;
                        }
                        commandToExecuteScript.add(commandLine);
                        commandLine = bufferedReader.readLine();
                    }
                    bufferedReader.close();
                    UserIO.setCommandsToEnter(commandToExecuteScript);
                    System.out.println(commandToExecuteScript);
                }
            } catch (IOException ex) {
                userIO.printerr("Нет прав на чтение файла!");
            } catch (ScriptRecursionException e) {
                userIO.printerr("Обнаружена рекурсия, данные из файла больше не читаются!");
                fileNames.clear();
            }
            return null;
        }else{
            throw new WrongValuesException("В команде execute_script должен быть аргумент - название файла");
        }
    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        return ResponseFactory.createResponse("Команда не дает ответов!");
    }

    @Override
    String getUsage() {
        return "execute_script - считывает команды с файла и выполняет их";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length!=0;
    }
}
