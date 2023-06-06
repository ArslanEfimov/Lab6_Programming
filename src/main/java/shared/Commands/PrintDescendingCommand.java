package main.java.shared.Commands;

import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.core.models.OrganizationCompareAnnualTurn;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

import java.util.Vector;
import java.util.stream.Collectors;

public class PrintDescendingCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    public PrintDescendingCommand(CollectionManager collectionManager) {
        super("print_descending");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(null);
        }
        else{
            throw new WrongValuesException("В команде info не должно быть аргументов, введите команду без аргументов");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        if(collectionManager.getCollectionVector().size()!=0) {
            return ResponseFactory.createResponse(String.valueOf(collectionManager.getCollectionVector().stream().
                    sorted(new OrganizationCompareAnnualTurn()).collect(Collectors.toCollection(Vector::new))));
        }
        else{
            return ResponseFactory.createResponse("Коллекция пуста!");
        }
    }

    @Override
    String getUsage() {
        return "print_descending - возвращает все элементы в порядке убывания";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
