import java.util.Scanner;

public class InnerThreadSync {
    // Shared member variable acting as the flag
    private boolean isEvenTurn = true;
    // Shared lock object
    private final Object lock = new Object(); 

    public void startPrinting(int start, int end) {
        
        // Inner Thread 1: Even
        Thread evenThread = new Thread(() -> {
            for (int i = start; i <= end; i++) {
                if (i % 2 == 0) {
                    synchronized (lock) {
                        while (!isEvenTurn) {
                            try { lock.wait(); } catch (InterruptedException e) {}
                        }
                        System.out.println("Inner Even: " + i);
                        isEvenTurn = false;
                        lock.notify();
                    }
                }
            }
        });

        // Inner Thread 2: Prime
        Thread primeThread = new Thread(() -> {
            for (int i = start; i <= end; i++) {
                if (isPrime(i)) {
                    synchronized (lock) {
                        while (isEvenTurn) {
                            try { lock.wait(); } catch (InterruptedException e) {}
                        }
                        System.out.println("Inner Prime: " + i);
                        isEvenTurn = true;
                        lock.notify();
                    }
                }
            }
        });

        evenThread.start();
        primeThread.start();
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) if (n % i == 0) return false;
        return true;
    }

    public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter START and END number");
      int start = sc.nextInt();
      int end = sc.nextInt();
        new InnerThreadSync().startPrinting(start, end);
    }
}

