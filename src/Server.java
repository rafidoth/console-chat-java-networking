import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    static HashMap<String,Connection> Connections = new HashMap<>();
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started");
        while(true){
            Socket socket = serverSocket.accept();
            new CreateConnection(socket);
            System.out.println("Client Connected");
            System.out.println("Now Connected Clients : "+ Connections.size());

        }
    }

    static class CreateConnection implements Runnable{
        Socket clientSocket;
        public CreateConnection(Socket clientSocket){
            this.clientSocket = clientSocket;
            new Thread(this).start();
        }
        @Override
        public void run() {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                oos.writeObject("Enter Your Name : ");
                Object clientName = null;
                clientName = ois.readObject();
                System.out.println( (String)clientName + " joined the server");
                Connection conn = new Connection(clientSocket,ois,oos);
                Connections.put(((String) clientName).toLowerCase(),conn);
                oos.writeObject("Connection Saved");
                new ServerReader(conn, Connections);
                new ServerWriter(oos);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    static class ServerClientCommandMode implements Runnable{
//        Socket clientSocket;
//        String clientName;
//        public ServerClientCommandMode(Socket clientSocket, String clientName){
//            this.clientSocket = clientSocket;
//            this.clientName = clientName;
//            new Thread(this).start();
//        }
//        @Override
//        public void run() {
//            try{
//                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
//                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
//                while(true){
//                        oos.writeObject("hellowed");
//                        System.out.println("test1");
//                        Object clientCommand = null;
//                        clientCommand = ois.readObject();
//                        System.out.println( (String)clientCommand + " commanded.");
//                        // for chat command = "$$chat$$"
//                        if(((String) clientCommand).equalsIgnoreCase("$$chat$$")){
//                            oos.writeObject("Enter Client Name : ");
//                            String recieverName = (String)ois.readObject();
//                            System.out.println(this.clientName + " wants to chat with "+ recieverName);
//                            if(Connections.containsKey(recieverName)){
//                                oos.writeObject("online");
//                                String receivedMessage = (String)ois.readObject();
//                                ObjectOutputStream receiverOS =
//                                        new ObjectOutputStream(Connections.get(recieverName).getOutputStream());
//                                receiverOS.writeObject(receivedMessage);
//                            }else{
//                                oos.writeObject("User is not online. Try again Later! ");
//                            }
//                        }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//    }
}
