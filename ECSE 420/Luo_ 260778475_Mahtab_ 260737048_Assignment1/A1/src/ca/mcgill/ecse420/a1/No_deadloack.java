package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class No_deadloack {

  private ReentrantLock leftStick;
  private ReentrantLock rightStick;



  public static void main(String[] args) {

    int numberOfPhilosophers = 5;
    Philosopher[] philosophers = new Philosopher[numberOfPhilosophers]; 
    ReentrantLock[] chopsticks = new ReentrantLock[numberOfPhilosophers];

    for (int i = 0; i < numberOfPhilosophers; i++) {
      chopsticks[i] = new ReentrantLock();
    }

    for (int i = 0; i < philosophers.length; i++) {
      ReentrantLock leftStick = chopsticks[i];
      ReentrantLock rightStick = chopsticks[i % numberOfPhilosophers];
      philosophers[i] = new Philosopher(leftStick, rightStick);

      Thread phil = new Thread(philosophers[i], "Phil: " + (i + 1));
      phil.start();



    }

  }


  public static class Philosopher implements Runnable {



    private ReentrantLock leftStick;
    private ReentrantLock rightStick;

    private boolean takeCS(ReentrantLock chopStick) throws InterruptedException {
      if (chopStick.tryLock(10, TimeUnit.MILLISECONDS)) {
        if (chopStick == this.leftStick)
          pickCS("Pick leftCS");
        else if (chopStick == rightStick)
          pickCS("Pick rightCS");
        return true;

      } else {
        return false;
      }
    }

    private boolean placeCS(ReentrantLock chopStick) throws InterruptedException {
      if (chopStick.tryLock(15, TimeUnit.MILLISECONDS)) {
        if (chopStick == this.leftStick)
          putCS("Pick leftCS");
        else if (chopStick == this.rightStick)
          putCS("Pick rightCS");
        return true;

      } else {
        return false;
      }
    }



    private static void pickCS(String stickSide) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + stickSide);
      Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of time

    }

    private static void think(String think) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + think);
      Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of time

    }

    private static void putCS(String stickSide) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + stickSide);
      Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of time

    }

    private static void eat(String stickSide) throws InterruptedException {
      System.out.println(Thread.currentThread().getName() + stickSide);
      Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of time

    }

    
    public Philosopher(ReentrantLock ls, ReentrantLock rs) {
      this.leftStick = ls;
      this.rightStick = rs;
    }




    @Override
    public void run() {
      try {
        while (true) {
          if (takeCS(leftStick)) {
            if (takeCS(rightStick)) {
              eat("Philosopher is eating");
              placeCS(rightStick);
              placeCS(leftStick);
              think("Thinking");

            } else {
              placeCS(leftStick);
            }
          } else {
            think("thinking");
          }
        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;

      }


    }



  }

}
