package main.java.shared.Commands;

import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.io.UserIO;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

import java.util.LinkedList;

public class UpdateCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private UserIO userIO;
    private LinkedList<String> update;
    public UpdateCommand(CollectionManager collectionManager){
        super("update_id");
        this.collectionManager = collectionManager;
        this.userIO = new UserIO();
        this.update = new LinkedList<>();
    }


    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)) {
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("В команде update_id должны быть аргументы типа (int)");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        Long id = Long.parseLong(requestBody.getArgs()[0]);
        if(collectionManager.getCollectionVector().stream().filter(el->el.getId()==id).count()==1){
            update.addLast("getId " + id);
            UserIO.setCommandsToEnter(update);
            return null;
        }
        else{
            return ResponseFactory.createResponse("Такого элемента нет в коллекции!");
        }
    }

    @Override
    String getUsage() {
        return "update_id - обновляет элемент коллекции по заданному id";
    }

    @Override
    public boolean checkArguments(String[] args) {
        if(args.length==0){
            return false;
        }
        else{
            try {
                Integer.parseInt(args[0].trim());
                return true;
            }catch (NumberFormatException ex){
                return false;
            }
        }
    }
}
