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

public class AddIfMaxCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private AskerOrganizations askerOrganizations;

    public AddIfMaxCommand(CollectionManager collectionManager) {
        super("add_if_max");
        this.collectionManager = collectionManager;
        this.askerOrganizations = new AskerOrganizations();

    }

    @Override
    public Request executeRequest(String[] args) throws WrongValuesException {
        if(checkArguments(args)){
            Organization organization = askerOrganizations.setOrganization();
            return RequestFactory.createRequestOrganization(getCommandName(), null, organization);

        }
        else{
            throw new WrongValuesException("В команде add_if_max не должно быть аргументов");
        }

    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        Organization organization = ((RequestBodyOrganization) requestBody).getOrganization();
        organization.setId(collectionManager.generateId());
        if(collectionManager.greaterThanMax(organization)) {
            collectionManager.addNewElement(organization);
            return ResponseFactory.createResponse("Новый элемент успешно добавлен в коллекцию!");
        }
        else {
            return ResponseFactory.createResponse("Элемент не удалось добавить в коллекцию, " +
                    "так как его значенение (annulTurnover) меньше максимального элемента");
        }
    }

    @Override
    String getUsage() {
        return "add_if_max - добавляет новый элемент в коллекцию, если его значение annualTurnover больше наибольшего в коллекции";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
