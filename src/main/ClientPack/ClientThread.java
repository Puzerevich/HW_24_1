package ClientPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;


// ClientThread listen answer from server/
public class ClientThread extends Thread {

    public BufferedReader getStreamIn() {
        return streamIn;
    }

    private BufferedReader streamIn;

    public ClientThread(Socket socket) throws IOException {
        streamIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true){
                String message = streamIn.readLine();
                System.out.println(message);
            }
        } catch (SocketException e) {
            System.out.println("Client disconnected.");
        } catch (IOException e){
            System.out.println(e);
        } finally {
            try {
                streamIn.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
