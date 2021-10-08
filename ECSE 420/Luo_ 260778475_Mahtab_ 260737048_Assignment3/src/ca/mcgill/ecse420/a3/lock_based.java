
package ca.mcgill.ecse420.a3;


import java.lang.reflect.Array;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;


public class lock_based<T> {

  private T[] items; // Array for queue
  private int head;
  private int tail;


  // Two locks to allow for parallelism
  private Lock headLock = new ReentrantLock();
  private Lock tailLock = new ReentrantLock();

  private Condition notEmpty = headLock.newCondition();
  private Condition notFull = tailLock.newCondition();

  public lock_based(int cap) {
    this.items = (T[]) new Object[cap];
    head = 0;
    tail = 0;
    this.headLock = new ReentrantLock();
    this.tailLock = new ReentrantLock();
    this.notEmpty = headLock.newCondition();
    this.notFull = tailLock.newCondition();
  }

  public void Enqueue(T item) throws InterruptedException {


    tailLock.lock();
    try {
      while (tail - head == items.length) {
        try {
          notFull.await(); // make current thread wait
        } catch (InterruptedException e) {
          System.out.println(e);
        }
      }

      items[(tail) % items.length] = item;
      tail++;

      if (tail - head == 1) {
        notEmpty.signal();
      }

    } finally {
      tailLock.unlock();
    }
  }


  public T Dequeue() throws InterruptedException {
    headLock.lock();
    T res;

    try {
      while (tail - head == 0) {
        try {
          notEmpty.await();
        } catch (InterruptedException e) {
          System.out.println(e);

        }
      }
      res = items[head % items.length];
      head++;
      
      if(tail-head == items.length-1) {
        notFull.signal();
      }
      return res;


    } finally {
      headLock.unlock();
    }
  }
}
