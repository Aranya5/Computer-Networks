import java.io.*;
import java.net.*;
import java.util.Scanner;

public class IterativeClient {

    public static void main(String[] args) {
        int PORT = 45000;
        String IP = "127.0.0.1";

        try {
            Socket cs = new Socket(IP, PORT);
            System.out.println("Connected to Server");

            DataInputStream dis = new DataInputStream(cs.getInputStream());
            DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message: ");
                String msg = sc.nextLine();

                dos.writeUTF(msg);

                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }

                String response = dis.readUTF();
                System.out.println("From Server: " + response);
            }

            dis.close();
            dos.close();
            cs.close();
            sc.close();

        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }
}