import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {
    ObjectInputStream ois;
    ObjectOutputStream oos;
    Socket socket;

    public Connection(Socket socket, ObjectInputStream ois, ObjectOutputStream oos){
        this.socket = socket;
        this.ois = ois;
        this.oos = oos;
    }
}
