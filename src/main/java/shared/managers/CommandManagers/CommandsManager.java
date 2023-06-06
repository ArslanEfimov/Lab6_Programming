package main.java.shared.managers.CommandManagers;


import main.java.shared.Commands.*;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.io.UserIO;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.Response;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandsManager implements CommandManagerInterface{

    private HashMap<String, AbstractCommand> commandsMapClient;

    private HashMap<String, AbstractCommand> commandsMapServer;
    private UserIO userIO;



    public CommandsManager(){
        this.commandsMapClient = new HashMap<>();
        this.commandsMapServer = new HashMap<>();
        this.userIO = new UserIO();
    }

    @Override
    public Map<String, ? extends AbstractCommand> getCommandsClient() {
        return commandsMapClient;
    }
    @Override
    public Map<String, ? extends AbstractCommand> getCommandsServer() {
        return commandsMapServer;
    }
    @Override
    public Request executeClient(String cmAndArgs) {
        String[] args = cmAndArgs.trim().split("\\s+");
        String commandName = args[0];
        AbstractCommand command;
        int commandArgumentsStartPosition = 1;
        args = Arrays.copyOfRange(args, commandArgumentsStartPosition, args.length);
        command = commandsMapClient.get(commandName);
        try {
            return command.executeRequest(args);
        }catch (WrongValuesException ex){
            userIO.printerr(ex.getMessage());
            userIO.println(" ");
        }
        return null;
    }

    @Override
    public Response executeServer(Request request) {
        return commandsMapServer.get(request.getCommand().getName()).executeResponse(request.getRequestBody());
    }

    public void fillClientMap(){
        HashMap<String, AbstractCommand> commandsMapClient = Arrays.asList(
                new InfoCommand(null),
                new ShowCommand(null),
                new ClearCommand(null),
                new RemoveFirstCommand(null),
                new PrintDescendingCommand(null),
                new RemoveByIdCommand(null),
                new FilterAnnualTurnoverCommand(null),
                new RemoveGreaterCommand(null),
                new CountGreaterThanOfficialAddressCommand(null),
                new AddCommand(null),
                new AddIfMaxCommand(null),
                new HelpCommand(null),
                new GetObjectForUpdateCommand(null),
                new ExitCommand(null),
                new ExecuteScriptCommand(null),
                new UpdateCommand(null)
        ).stream().collect(Collectors.toMap(AbstractCommand::getCommandName,
                Function.identity(),
                (existing, replacement) -> existing,
                HashMap::new));
        this.commandsMapClient = commandsMapClient;
    }

    public void fillServerMap(CollectionManager collectionManager){
        HashMap<String, AbstractCommand> commandsMapServer = Arrays.asList(
                new InfoCommand(collectionManager),
                new ShowCommand(collectionManager),
                new ClearCommand(collectionManager),
                new RemoveFirstCommand(collectionManager),
                new PrintDescendingCommand(collectionManager),
                new RemoveByIdCommand(collectionManager),
                new FilterAnnualTurnoverCommand(collectionManager),
                new RemoveGreaterCommand(collectionManager),
                new CountGreaterThanOfficialAddressCommand(collectionManager),
                new AddCommand(collectionManager),
                new AddIfMaxCommand(collectionManager),
                new HelpCommand(collectionManager),
                new GetObjectForUpdateCommand(collectionManager),
                new ExitCommand(collectionManager),
                new ExecuteScriptCommand(collectionManager),
                new UpdateCommand(collectionManager)
        ).stream().collect(Collectors.toMap(AbstractCommand::getCommandName,
                Function.identity(),
                (existing, replacement) -> existing,
                HashMap::new));
        this.commandsMapServer = commandsMapServer;
    }

    public boolean commandCheck(String command){
        if(commandsMapClient.containsKey(command)){
            return true;
        }
        else{
            userIO.printerr("Такой команды нет! Воспользуйтесь командой help, чтобы увидеть список возможных команд");
            return false;
        }
    }
}
