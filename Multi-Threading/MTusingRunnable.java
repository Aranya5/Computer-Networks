import java.util.Scanner;

class EvenRunnable implements Runnable {
  int start, end;

  public EvenRunnable(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public void run() {
    for (int i = start; i <= end; i++) {
      if (i % 2 == 0)
        System.out.println("Even (Runnable): " + i);
    }
  }
}

class PrimeRunnable implements Runnable {
  int start, end;

  public PrimeRunnable(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public void run() {
    for (int i = start; i <= end; i++) {
      if (isPrime(i))
        System.out.println("Prime (Runnable): " + i);
    }
  }

  // isPrime helper method identical to above
  private boolean isPrime(int n) {
    if (n <= 1)
      return false;
    for (int i = 2; i <= Math.sqrt(n); i++)
      if (n % i == 0)
        return false;
    return true;
  }
}

public class MTusingRunnable {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter START and END number");
    int start = sc.nextInt();
    int end = sc.nextInt();
    Thread t1 = new Thread(new EvenRunnable(start, end));
    Thread t2 = new Thread(new PrimeRunnable(start, end));
    t1.start();
    t2.start();
  }
}
