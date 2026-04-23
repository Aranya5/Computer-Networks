import java.util.Scanner;

class EvenThread extends Thread {
  int start, end;

  public EvenThread(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public void run() {
    for (int i = start; i <= end; i++) {
      if (i % 2 == 0) {
        System.out.println("Even: " + i);
      }
    }
  }
}

class PrimeThread extends Thread {
  int start, end;

  public PrimeThread(int start, int end) {
    this.start = start;
    this.end = end;
  }

  public void run(){
    for(int i = start; i <= end; i++){
       if(isPrime(i)){
        System.out.println("Prime: "+i);
       }
    }   
  }

  private boolean isPrime(int n){
    if(n<=1) return false;
    for(int i = 2; i <= Math.sqrt(n); i++){
      if(n%i == 0) return false;
    }
    return true;
  }
}

public class MTusingThreadClass{
  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter START and END number");
    int s = sc.nextInt();
    int e = sc.nextInt();

    EvenThread t1 = new EvenThread(s, e);
    PrimeThread t2 = new PrimeThread(s, e);

    t1.start();
    t2.start();

  }
}
