package main.java.server.ServerConnection;

import main.java.shared.managers.CollectionManager;
import main.java.shared.managers.CommandManagers.CommandsManager;
import main.java.shared.managers.FileManagerReader;
import main.java.shared.managers.FileManagerWriter;
import main.java.shared.network.Deserializers.Deserializer;
import main.java.shared.network.Serializers.Serializer;
import main.java.shared.network.messaging.Request;
import main.java.shared.network.messaging.Response;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class ServerConnection {

    private final int port;
    private final InetSocketAddress address;
    private ByteBuffer byteBuffer;
    private final int BUFFER = 2048;
    private Selector selector;
    private CommandsManager commandsManager;
    private FileManagerReader fileManagerReader;
    private CollectionManager collectionManager;
    private FileManagerWriter fileManagerWriter;
    private static Logger logger = Logger.getLogger(ServerConnection.class.getName());

    public ServerConnection(int port, CollectionManager collectionManager, FileManagerReader fileManagerReader) {
        this.port = port;
        String fileName = new String();
        this.address = new InetSocketAddress(port);
        commandsManager = new CommandsManager();
        this.fileManagerReader = new FileManagerReader(fileName);
        this.collectionManager = new CollectionManager(fileManagerReader);
        commandsManager.fillServerMap(collectionManager);
        this.fileManagerWriter = new FileManagerWriter(collectionManager,fileManagerReader);
    }

    public void waitConnection() throws IOException, ClassNotFoundException, InterruptedException {
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int readyChannel = selector.select();
                if (readyChannel == 0) {
                    continue;
                }
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                whichSelectedKey(iterator);
            }
        }catch (AlreadyBoundException ex){
            logger.warning("Соединение уже установлено" + ex);
        }catch (UnsupportedAddressTypeException ex){
            logger.warning("Адрес уже занят" + ex);
        }
    }
    private void whichSelectedKey(Iterator<SelectionKey> keyIterator) throws IOException{
        while(keyIterator.hasNext()){
            SelectionKey key = keyIterator.next();
            keyIterator.remove();
            if(!key.isValid()){
                continue;
            }
            if(key.isAcceptable()){
                newConnection(key);
            }
            else if(key.isReadable()){
                    readData(key);
            }
        }
    }

    private void newConnection(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        logger.info("Клиент " + channel.getRemoteAddress() +  " подключился!");
        if(channel == null){
            return;
        }
        channel.configureBlocking(false);
        key.attach(channel); // Привязка клиентского сокета к ключу
        channel.register(selector, SelectionKey.OP_READ); // Регистрация ключа в селекторе
        selector.wakeup();

    }

    private void readData(SelectionKey key) throws IOException{
        byteBuffer = ByteBuffer.allocate(BUFFER);
        SocketChannel channel = (SocketChannel) key.channel();
        int bytesRead = channel.read(byteBuffer);
        if (bytesRead == -1) {
            key.cancel();
            channel.close();
            logger.info("Клиент отключился");
        }
        else {
            byte[] bytes = new byte[bytesRead];
            System.arraycopy(byteBuffer.array(), 0, bytes, 0, bytesRead);
            Request request = Deserializer.deserializeRequest(bytes);
            logger.info("Клиент " + channel.getRemoteAddress() + " отправил запрос: " + request.toString());
            Response response = commandsManager.executeServer(request);
            write(key, response);
        }
    }

    private void write(SelectionKey key, Response response) throws IOException {
        byte[] buffer;
        SocketChannel channel = (SocketChannel) key.channel();
        buffer = Serializer.serializeResponse(response);
        byteBuffer = ByteBuffer.wrap(buffer);
        while(byteBuffer.hasRemaining()){
            channel.write(byteBuffer);
        }
    }

}
