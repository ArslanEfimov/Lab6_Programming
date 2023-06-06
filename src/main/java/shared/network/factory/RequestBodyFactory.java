package main.java.shared.network.factory;

import main.java.shared.core.models.*;
import main.java.shared.network.messaging.RequestBody;
import main.java.shared.network.messaging.RequestBodyAddress;
import main.java.shared.network.messaging.RequestBodyOrganization;

public class RequestBodyFactory {

    public RequestBodyFactory(){
    }

    public static RequestBody createRequestBody(String[] args){
        return new RequestBody(args);
    }

    public static RequestBody createRequestBodyAddress(String[] args, Address address){
        return new RequestBodyAddress(args, address);
    }

    public static RequestBody createRequestBodyOrganization(String[] args, Organization organization){
        return new RequestBodyOrganization(args, organization);
    }
}
