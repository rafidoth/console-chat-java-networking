import java.io.IOException;
import java.io.ObjectInputStream;

public class ReaderThread implements Runnable{
    ObjectInputStream ois;
    public ReaderThread(ObjectInputStream ois){
        this.ois = ois;
        new Thread(this).start();
    }
    @Override
    public void run() {
        while(true){
            Object fromServer = null;
            try {
                fromServer = ois.readObject();
                System.out.println( (String)fromServer);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
