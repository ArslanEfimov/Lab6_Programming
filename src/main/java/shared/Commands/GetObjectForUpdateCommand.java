package main.java.shared.Commands;


import main.java.shared.core.Exceptions.WrongValuesException;
import main.java.shared.core.models.Asker.AskerOrganizations;
import main.java.shared.core.models.Organization;
import main.java.shared.managers.CollectionManager;
import main.java.shared.network.factory.RequestFactory;
import main.java.shared.network.factory.ResponseFactory;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.RequestBodyOrganization;
import main.java.shared.network.messaging.Response;

public class GetObjectForUpdateCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private AskerOrganizations askerOrganizations;

    public GetObjectForUpdateCommand(CollectionManager collectionManager) {
        super("getId");
        this.collectionManager = collectionManager;
        this.askerOrganizations = new AskerOrganizations();

    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)) {
                Organization organization = askerOrganizations.setOrganization();
                return RequestFactory.createRequestOrganization(getCommandName(), args, organization);
        }

        else{
            throw new WrongValuesException("В команде update_id должны быть аргументы типа (int)");
        }

    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        Long id = Long.parseLong(requestBody.getArgs()[0]);
        if(collectionManager.getCollectionVector().size()!=0) {
            collectionManager.getCollectionVector().removeIf(elem -> elem.getId() == id);
            Organization organization = ((RequestBodyOrganization) requestBody).getOrganization();
            organization.setId(id);
            collectionManager.addNewElement(organization);
            return ResponseFactory.createResponse("Элемент успешно обновлен!");

        }
        else{
            return ResponseFactory.createResponse("Коллекция пуста!");
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
