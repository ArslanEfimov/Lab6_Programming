package main.java.shared.Commands;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class ClearCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if (checkArguments(args)) {
            return super.executeRequest(null);
        }
        else{
            throw new WrongValuesException("В команде clear не должно быть аргументов, введите команду без аргументов");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        return ResponseFactory.createResponse(collectionManager.clearCollection());
    }

    @Override
    String getUsage() {
        return "clear - команда очищает коллекцию";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
