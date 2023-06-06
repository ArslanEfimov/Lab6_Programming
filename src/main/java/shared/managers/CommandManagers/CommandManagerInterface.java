package main.java.shared.managers.CommandManagers;


import main.java.shared.Commands.AbstractCommand;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.Response;

import java.util.Map;

public interface CommandManagerInterface {

    Map<String, ? extends AbstractCommand> getCommandsClient();
    Map<String, ? extends AbstractCommand> getCommandsServer();

    Request executeClient(String cmAndArgs);

    Response executeServer(Request request);

}
