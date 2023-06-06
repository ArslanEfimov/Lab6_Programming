package main.java.shared.core.models;

import java.io.Serializable;

/**
 * Organization enum OrganizationType
 */
public enum OrganizationType{
    COMMERCIAL("коммерческий"),
    PUBLIC("публичный"),
    GOVERNMENT("правительство"),
    TRUST("доверие"),
    PRIVATE_LIMITED_COMPANY("закрытое акционерное общество");

    private String title;

    OrganizationType(String title){
        this.title = title;
    }
    public String getTitle(){
        return title;
    }
    @Override
    public String toString(){
        return title;
    }
}
