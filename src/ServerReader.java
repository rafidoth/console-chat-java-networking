import java.io.IOException;
import java.util.HashMap;

public class ServerReader implements Runnable{
    Connection conn ;
    HashMap<String,Connection> ConnectionList;
    public ServerReader(Connection conn, HashMap Connections){
        this.ConnectionList = Connections;
        this.conn = conn;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            try {
                String received = (String)conn.ois.readObject();
                // receiver##message
                String[] arr = received.split("##");
                Connection receiver = ConnectionList.get(arr[0].toLowerCase());
                receiver.oos.writeObject(arr[1]);
                System.out.println("From Client : " + received);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
