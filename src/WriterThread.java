import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class WriterThread implements Runnable{
   ObjectOutputStream oos;
    public WriterThread(ObjectOutputStream oos){
        this.oos = oos;
        new Thread(this).start();
    }
    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        while(true){
            String fromClient = in.nextLine();
            try {
                oos.writeObject(fromClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
