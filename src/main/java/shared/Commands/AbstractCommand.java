package main.java.shared.Commands;


import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.network.factory.RequestFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

abstract public class AbstractCommand {
    private final String commandName;

    protected AbstractCommand(String commandName) {
        this.commandName = commandName;
    }
    abstract public Response executeResponse(RequestBody requestBody);

    public Request executeRequest(String[] args) throws WrongValuesException {
        return RequestFactory.createRequest(getCommandName(), args);
    }

    abstract String getUsage();

    public String getCommandName(){
        return commandName;
    }

    abstract public boolean checkArguments(String[] args);

}
