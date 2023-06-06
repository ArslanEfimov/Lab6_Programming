package main.java.shared.Commands;

import main.java.shared.core.Exceptions.InvalidExecuteRequestException;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class RemoveByIdCommand extends AbstractCommand{
    
    private final CollectionManager collectionManager;
    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("remove_by_id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("В команде remove_by_id должны быть аргументы типа (Long) и большие 0");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        if(collectionManager.getCollectionVector().size()!=0) {
            Long id = Long.parseLong(requestBody.getArgs()[0]);
            if (collectionManager.removeById(id)) {
                return ResponseFactory.createResponse("Элемент с данным id успешно удален!");
            } else {
                return new Response((new InvalidExecuteRequestException("Элемента с таким id нет в коллекции!")).toString());
            }
        }
        else{
            return ResponseFactory.createResponse("Коллекция пуста!");
        }
    }

    @Override
    String getUsage() {
        return "remove_by_id - удаляет элемент из коллекции по его id";
    }

    @Override
    public boolean checkArguments(String[] args) {
        if(args.length==0) {
            return false;
        }
        else{
            try{
                if(Long.parseLong(args[0])<=0){
                    return false;
                }
                Long.parseLong(args[0]);
                return true;
            }catch (NumberFormatException ex){
                return false;
            }
            
        }
    }
}
