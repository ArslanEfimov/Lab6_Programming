package main.java.shared.Commands;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.managers.CommandManagers.CommandsManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

import java.util.ArrayList;

public class HelpCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private CommandsManager commandsManager;
    public HelpCommand(CollectionManager collectionManager) {
        super("help");
        this.collectionManager = collectionManager;
        commandsManager = new CommandsManager();
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(null);
        }
        else{
            throw new WrongValuesException("В команде help не должно быть аргументов!");
        }
    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        commandsManager.fillClientMap();
        ArrayList<String> commandHelpList = new ArrayList<>();
        commandsManager.getCommandsClient().values().forEach(command -> {
            String usage = command.getUsage();
            commandHelpList.add(usage);
        });
        return ResponseFactory.createResponse(String.join("\n",commandHelpList));
    }

    @Override
    String getUsage() {
        return "help - выводит список всех команд с их описанием";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
