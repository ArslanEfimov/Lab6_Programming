package main.java.shared.Commands;


import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.Response;
import main.java.shared.core.models.Organization;

import java.util.stream.Collectors;

public class FilterAnnualTurnoverCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    public FilterAnnualTurnoverCommand(CollectionManager collectionManager) {
        super("filter_by_annual_turnover");
        this.collectionManager = collectionManager;
    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            return super.executeRequest(args);
        }
        else{
            throw new WrongValuesException("В команде count_greater_than_official_address должны быть аргументы типа (Float) и большие 0");
        }
    }
    @Override
    public Response executeResponse(RequestBody requestBody) {
        if(collectionManager.getCollectionVector().size()!=0) {
            Float annualTurnover = Float.parseFloat(requestBody.getArgs()[0]);
            return ResponseFactory.createResponse(collectionManager.getCollectionVector().stream().
                    filter(Organization -> Organization.getAnnualTurnover() > annualTurnover)
                    .map(Organization::toString)
                    .collect(Collectors.joining(",")));
        }
        else{
            return ResponseFactory.createResponse("Коллекция пуста!");
        }
    }

    @Override
    String getUsage() {
        return "count_greater_than_official_address - вывод все элементы коллекции, " +
                "у которых значение AnnualTurnover больще чем заданное";
    }

    @Override
    public boolean checkArguments(String[] args) {
        if(args.length==0)
            return false;
        else{
            try{
                Float annualTurnover = Float.parseFloat(args[0].trim().replace(",","."));
                if(annualTurnover<=0){
                    return false;
                }
                return true;
            }catch (NumberFormatException ex){
                return false;
            }
        }
    }
}
