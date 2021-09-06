package BigTaskChat.client;

import BigTaskChat.Connection;
import BigTaskChat.ConsoleHelper;
import BigTaskChat.Message;
import BigTaskChat.MessageType;
import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected;

    public static void main (String [] args){
        Client client = new Client();
        client.run();
    }

    public void run(){
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        // Помечать созданный поток как daemon, это нужно для того,
        // чтобы при выходе из программы вспомогательный поток прервался автоматически.
        socketThread.start();

        try {
            synchronized (this){
                wait();
            }
        } catch (Exception exception){
            ConsoleHelper.writeMessage("Ошибка, выход из программы!");
            return;
        }

        if(clientConnected == true){
            ConsoleHelper.writeMessage("Соеденение установлено! Для выхода наберите команду 'exit'");
        } else ConsoleHelper.writeMessage("Произошла ошибка во время работы клиента.");

        while (clientConnected == true){
            try {
                String str = ConsoleHelper.readString();
                if (str.equals("exit")){
                    break;
                }

                if (shouldSendTextFromConsole()){
                    sendTextMessage(str);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    public class SocketThread extends Thread {

        public void run(){
            try {
                String str = getServerAddress();
                int port = getServerPort();
                Socket socket = new Socket(str, port);
                connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException exception) {
                notifyConnectionStatusChanged(false);
            }
        }

        protected void processIncomingMessage(String message) throws IOException {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " присоеденился к чату!");
        }

        protected void informAboutDeletingNewUser(String userName){
            ConsoleHelper.writeMessage(userName + " покинул чат!!");
        }

        protected void notifyConnectionStatusChanged(boolean clientConnected){
            Client.this.clientConnected = clientConnected;
            synchronized (Client.this){
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException{
            //метод, который представляет клиента серверу
            while (true){
                Message message = connection.receive();

                if (message.getType() == MessageType.NAME_REQUEST ){
                    String name = getUserName();
                    connection.send(new Message(MessageType.USER_NAME, name));
                } else {
                    if (message.getType() == MessageType.NAME_ACCEPTED){
                        notifyConnectionStatusChanged(true);
                        return;
                    } else throw new IOException("Unexpected MessageType");
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException{

            while (true){
                Message message = connection.receive();

                if (message.getType() == MessageType.TEXT){
                    processIncomingMessage(message.getData());
                } else if (message.getType() == MessageType.USER_ADDED){
                    informAboutAddingNewUser(message.getData());
                } else if (message.getType() == MessageType.USER_REMOVED){
                    informAboutDeletingNewUser(message.getData());
                } else throw new IOException("Unexpected MessageType");
            }

        }

    }

    protected String getServerAddress() throws IOException {
        ConsoleHelper.writeMessage("Введите адрес сервера ");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() throws IOException {
        ConsoleHelper.writeMessage("Введите порт ");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() throws IOException {
        ConsoleHelper.writeMessage("Введите имя пользователя ");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole(){
        return true;
    }

    protected SocketThread getSocketThread(){
        return new SocketThread();
    }

    protected void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (Exception exception){
            clientConnected = false;
        }
    }
}
