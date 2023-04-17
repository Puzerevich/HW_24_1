package ClientPack;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class SendFileThread extends Thread{

    private String fileName;

    public SendFileThread(String name) {
        this.fileName = name;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("localhost", 8082)) {

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            File file = new File(fileName);
            if(!file.isFile()) {
                System.out.println("Try again, file is not founded. ");
                dataOutputStream.close();
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(file);

            dataOutputStream.writeUTF(fileName);

            dataOutputStream.writeLong(file.length());

            byte[] buffer = new byte[5 * 1024];
            int filePart;

            // When file is big, sent part of file.
            while ((filePart = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, filePart);
                dataOutputStream.flush();
            }

            System.out.println("File was sent");

            fileInputStream.close();
            dataOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
