import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);

            Socket s = new Socket("127.0.0.1", 40000);
            System.out.println("Connected to Server");
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.print("Enter message to send to server: ");
            String userInput = sc.nextLine();

            dos.writeUTF(userInput);
            System.out.println("Message sent successfully.");

            dos.close();
            s.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}