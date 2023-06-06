package main.java.shared.Commands;

import main.java.shared.core.Exceptions.InvalidExecuteRequestException;
import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;

public class RemoveGreaterCommand extends AbstractCommand{

    private CollectionManager collectionManager;
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        super("remove_greater");
        this.collectionManager = collectionManager;
    }
    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("В команде remove_greater должны быть аргументы типа (Float) большие 0");
        }
    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        if(collectionManager.getCollectionVector().size()!=0) {
            Float annualTurnover = Float.parseFloat(requestBody.getArgs()[0].trim().replace(",", "."));
            if (collectionManager.removeGreater(annualTurnover)) {
                return ResponseFactory.createResponse("Элемент успешно удален!");
            }
            else {
                return new Response((new InvalidExecuteRequestException("Элемента с таким annualTurnover нет в коллекции!")).toString());
            }
        }else{
            return ResponseFactory.createResponse("Коллекция пуста!");
        }
    }

    @Override
    String getUsage() {
        return "remove_greater - удаляет элемент из коллекции, чье значение равно вводимому значению annualturnover";
    }

    @Override
    public boolean checkArguments(String[] args) {
        if(args.length == 0) {
            return false;
        }
        else{
            try{
                Float annualTurnover = Float.parseFloat(args[0].trim().replace(",","."));
                if(annualTurnover<=0){
                    return false;
                }
                return true;
            }catch (NumberFormatException ex) {
                return false;
            }
        }
    }
}
