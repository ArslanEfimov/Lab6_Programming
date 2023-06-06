package main.java.shared.Commands;

import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class RemoveFirstCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    public RemoveFirstCommand(CollectionManager collectionManager) {
        super("remove_first");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(null);
        }
        else{
            throw new WrongValuesException("В команде remove_first не должно быть аргументов, введите команду без аргументов");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        if(collectionManager.getCollectionVector().size()!=0) {
            return ResponseFactory.createResponse(collectionManager.removeFirst());
        }
        else{
            return ResponseFactory.createResponse("Коллекция пуста!");
        }
    }

    @Override
    String getUsage() {
        return "remove_first - удаляет 1 элемент из коллекции";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
