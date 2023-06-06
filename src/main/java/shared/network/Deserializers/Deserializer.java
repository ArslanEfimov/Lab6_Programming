package main.java.shared.network.Deserializers;

import main.java.shared.core.Exceptions.DeserializeException;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {

    public static Request deserializeRequest(byte[] serializedObject){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Request) objectInputStream.readObject();
        }catch (IOException |  ClassNotFoundException ex){
            throw new DeserializeException("десериализовать запрос не удается!");
        }
    }
    public static Response deserializeResponse(byte[] serializedObject){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(serializedObject);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (Response) objectInputStream.readObject();
        }catch (IOException |  ClassNotFoundException ex){
            throw new DeserializeException("десериализовать ответ не удается!");
        }
    }
}
