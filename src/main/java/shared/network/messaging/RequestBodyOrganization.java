package main.java.shared.network.messaging;


import main.java.shared.core.models.Organization;

public class RequestBodyOrganization extends RequestBody{

    private final Organization organization;
    public RequestBodyOrganization(String[] args, Organization organization) {
        super(args);
        this.organization = organization;
    }

    public Organization getOrganization(){
        return organization;
    }
}
