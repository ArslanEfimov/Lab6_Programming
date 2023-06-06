package main.java.shared.network.messaging;

import main.java.shared.network.commandsdto.CommandDTO;

import java.io.Serializable;

public class Request implements Serializable {
    private CommandDTO command;
    private RequestBody requestBody;
    private RequestBodyAddress requestBodyAddress;
    private RequestBodyOrganization requestBodyOrganization;

    public Request(CommandDTO command, RequestBody requestBody){
        this.command = command;
        this.requestBody = requestBody;
    }
    public Request(CommandDTO command, RequestBodyAddress requestBodyAddress){
        this.command = command;
        this.requestBodyAddress = requestBodyAddress;
    }
    public Request(CommandDTO command, RequestBodyOrganization requestBodyOrganization){
        this.command = command;
        this.requestBodyOrganization = requestBodyOrganization;
    }


    public CommandDTO getCommand(){
        return command;
    }

    public RequestBody getRequestBody(){
        return requestBody;
    }

    @Override
    public String toString() {
        return String.valueOf(command) + ", аргументы команды: " + String.valueOf(requestBody);
    }
}
