package main.java.shared.Commands;


import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class InfoCommand extends AbstractCommand{

    private final CollectionManager collectionManager;

    public InfoCommand(CollectionManager collectionManager) {
        super("info");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response executeResponse(RequestBody requestBody){
        return new Response(collectionManager.toString());
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)) {
            return super.executeRequest(null);
        }
        else{
            throw new WrongValuesException("В команде info не должно быть аргументов, введите команду без аргументов");
        }
    }

    @Override
    String getUsage() {
        return "info - выводит информацию о коллекции";
    }

    @Override
    public boolean checkArguments(String[] args){
        return args.length==0;
    }
}
