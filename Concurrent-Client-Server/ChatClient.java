import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) throws IOException {

        String IP = "127.0.0.1";
        int PORT = 47000;

        Socket s = new Socket(IP, PORT);
        System.out.println("Connected to Server");

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);

        // READ THREAD
        Thread readThread = new Thread(() -> {
            try {
                while (true) {
                    String msg = dis.readUTF();

                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Server closed connection");
                        break;
                    }

                    System.out.println("\nServer: " + msg);
                }
            } catch (Exception e) {
                System.out.println("Connection closed");
            }
        });

        // WRITE THREAD
        Thread writeThread = new Thread(() -> {
            try {
                while (true) {
                    String msg = sc.nextLine();
                    dos.writeUTF(msg);

                    if (msg.equalsIgnoreCase("exit")) {
                        s.close();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error sending message");
            }
        });

        readThread.start();
        writeThread.start();
    }
}