package ca.mcgill.ecse420.a1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock_dining_philosophers {

  private ReentrantLock leftStick;
  private ReentrantLock rightStick;


  private static void pickCS(String stickSide) throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + stickSide);
    Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of
                                                 // time

  }

  private static void think(String think) throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + think);
    Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of
                                                 // time

  }

  private static void placeCS(String stickSide) throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + stickSide);
    Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of
                                                 // time

  }

  private static void eat(String stickSide) throws InterruptedException {
    System.out.println(Thread.currentThread().getName() + stickSide);
    Thread.sleep((long) (Math.random() * 1000)); // make the thread sleep for a random amount of
                                                 // time

  }

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


    public Philosopher(ReentrantLock ls, ReentrantLock rs) {
      this.leftStick = ls;
      this.rightStick = rs;
    }



    @Override
    public void run() {
      try {
        while (true) {
          think("Philosopher is thinking");
          synchronized (leftStick) { // the philosoper thinks and then starts to eat
            pickCS("leftStick");
            synchronized (rightStick) {
              eat("get rightStick - eating");
              placeCS("put down rightStick");
            }
          }
          placeCS("put back leftStick and start thinking");



        }
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;

      }


    }



  }

}
