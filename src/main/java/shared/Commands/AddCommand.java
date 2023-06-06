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

import java.time.LocalDate;

public class AddCommand extends AbstractCommand{

    private final CollectionManager collectionManager;
    private AskerOrganizations askerOrganizations;

    public AddCommand(CollectionManager collectionManager) {
        super("add");
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
            throw new WrongValuesException("В команде add не должно быть аргументов");
        }

    }

    @Override
    public Response executeResponse(RequestBody requestBody) {
        Organization organization = ((RequestBodyOrganization) requestBody).getOrganization();
        organization.setId(collectionManager.generateId());
        collectionManager.addNewElement(organization);
        return ResponseFactory.createResponse("Новый элемент успешно добавлен в коллекцию!");
    }

    @Override
    String getUsage() {
        return "add - добавляет новый элемент в коллекцию";
    }

    @Override
    public boolean checkArguments(String[] args) {
        return args.length==0;
    }
}
