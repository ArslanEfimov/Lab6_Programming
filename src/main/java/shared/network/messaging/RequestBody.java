package main.java.shared.network.messaging;

import java.io.Serializable;
import java.util.Arrays;

public class RequestBody implements Serializable{
    private final String[] args;

    public RequestBody(String[] args) {
        this.args = args;
    }

    public String[] getArgs(){
        return args;
    }

    @Override
    public String toString() {
        return Arrays.toString(args);
    }
}
