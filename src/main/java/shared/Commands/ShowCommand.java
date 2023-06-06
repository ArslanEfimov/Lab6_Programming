package main.java.shared.Commands;

import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class ShowCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }


    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("В команде show не должно быть аргументов, введите команду без аргументов");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        return ResponseFactory.createResponse(collectionManager.getCollectionVector().toString());
    }

    @Override
    String getUsage() {
        return "show - команда выводит все элементы в коллекции";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
