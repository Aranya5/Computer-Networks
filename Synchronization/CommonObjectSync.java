import java.util.Scanner;

class SharedPrinter {
  boolean isEvenTurn = true;

  synchronized void printEven(int num) {
    while (!isEvenTurn) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    System.out.println("Even: " + num);
    isEvenTurn = false;
    notify();
  }

  synchronized void printPrime(int num) {
    while (isEvenTurn) {
      try {
        wait();
      } catch (InterruptedException e) {
      }
    }
    System.out.println("Prime: " + num);
    isEvenTurn = true;
    notify();
  }
}

// The threads are similar to Part 1, but they call the SharedPrinter methods
class SyncEvenThread extends Thread {
  SharedPrinter printer;
  int start, end;

  SyncEvenThread(SharedPrinter p, int s, int e) {
    printer = p;
    start = s;
    end = e;
  }

  public void run() {
    for (int i = start; i <= end; i++) {
      if (i % 2 == 0)
        printer.printEven(i);
    }
  }
}

class SyncPrimeThread extends Thread {
  SharedPrinter printer;
  int start, end;

  SyncPrimeThread(SharedPrinter p, int s, int e) {
    printer = p;
    start = s;
    end = e;
  }

  public void run() {
    for (int i = start; i <= end; i++) {
      if (isPrime(i))
        printer.printPrime(i);
    }
  }

  private boolean isPrime(int n) {
    if (n <= 1)
      return false;
    for (int i = 2; i <= Math.sqrt(n); i++)
      if (n % i == 0)
        return false;
    return true;
  }
}

public class CommonObjectSync {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter START and END number");
    int start = sc.nextInt();
    int end = sc.nextInt();
    SharedPrinter sharedObj = new SharedPrinter();
    new SyncEvenThread(sharedObj, start, end).start();
    new SyncPrimeThread(sharedObj, start, end).start();
  }
}
