import java.io.*;
import java.net.*;

public class IterativeServer {

    public static void main(String[] args) {
        int PORT = 45000;

        try {
            ServerSocket ss = new ServerSocket(PORT);
            System.out.println("Server is running on PORT: " + PORT);

            while (true) {
                Socket s = ss.accept();
                System.out.println("Client connected");

                InetAddress ip = s.getInetAddress();
                System.out.println("Client IP: " + ip.getHostAddress());
                System.out.println("Client Port: " + s.getPort());

                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                // Handle multiple messages from SAME client
                while (true) {
                    String msg = dis.readUTF();
                    System.out.println("From Client: " + msg);

                    if (msg.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected");
                        break;
                    }

                    dos.writeUTF("Hello from Server");
                }

                dis.close();
                dos.close();
                s.close();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}
