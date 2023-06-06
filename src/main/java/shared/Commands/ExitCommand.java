package main.java.shared.Commands;

import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.io.UserIO;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class ExitCommand extends AbstractCommand{

    private final CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager) {
        super("exit");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            UserIO.setClientWork(false);
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("Команда exit не имеет аргументов!");
        }
    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        return ResponseFactory.createResponse("До следующих встреч!");
    }

    @Override
    String getUsage() {
        return "exit - команда завершает работу клиентской части";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
