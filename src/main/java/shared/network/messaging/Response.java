package main.java.shared.network.messaging;

import java.io.Serializable;


public class Response implements Serializable {
    private String serverMessage;
    private boolean serverBol;
    public Response (String serverMessage){
        this.serverMessage = serverMessage;
    }

    public Response (boolean serverBol){
        this.serverBol = serverBol;
    }
    public String getServerMessage(){
        return serverMessage;
    }

    @Override
    public String toString(){
        return serverMessage;
    }
}
