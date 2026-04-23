import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatServer {

    static AtomicInteger clientCount = new AtomicInteger(0);
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        int PORT = 47000;
        ServerSocket ss = new ServerSocket(PORT);

        System.out.println("Chat Server running on PORT " + PORT);

        while (true) {
            Socket s = ss.accept();
            int clientId = clientCount.incrementAndGet();

            new Thread(new ClientHandler(s, clientId)).start();
        }
    }
}

class ClientHandler implements Runnable {

    Socket s;
    int clientId;

    ClientHandler(Socket s, int clientId) {
        this.s = s;
        this.clientId = clientId;
    }

    public void run() {
        try {
            String ip = s.getInetAddress().getHostAddress();
            int port = s.getPort();

            System.out.println("Client-" + clientId + " connected [" + ip + ":" + port + "]");

            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            // THREAD 1 → READ FROM CLIENT
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = dis.readUTF();

                        if (msg.equalsIgnoreCase("exit")) {
                            System.out.println("Client-" + clientId + " disconnected");
                            break;
                        }

                        System.out.println("\nClient-" + clientId + " [" + ip + ":" + port + "] -> " + msg);
                    }
                } catch (Exception e) {
                    System.out.println("Client-" + clientId + " read error");
                }
            });

            // THREAD 2 → WRITE TO CLIENT (SERVER INPUT)
            Thread writeThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg;

                        synchronized (ChatServer.sc) {
                            System.out.print("Reply to Client-" + clientId + ": ");
                            msg = ChatServer.sc.nextLine();
                        }

                        dos.writeUTF(msg);

                        if (msg.equalsIgnoreCase("exit")) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Client-" + clientId + " write error");
                }
            });

            readThread.start();
            writeThread.start();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
