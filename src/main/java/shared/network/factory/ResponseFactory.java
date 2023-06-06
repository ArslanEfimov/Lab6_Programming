package main.java.shared.network.factory;


import main.java.shared.network.messaging.Response;

public class ResponseFactory {

    public ResponseFactory(){}

    public static Response createResponse(String serverMessage){
        return new Response(serverMessage);
    }
    public static Response createResponse(boolean serverBol){
        return new Response(serverBol);
    }
}
