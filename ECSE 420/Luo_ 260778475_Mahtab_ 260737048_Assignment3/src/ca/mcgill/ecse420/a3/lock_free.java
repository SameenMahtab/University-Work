
package ca.mcgill.ecse420.a3;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;

public class lock_free {

  private Object[] items;
  private AtomicInteger head;
  private AtomicInteger tail;
  private AtomicInteger enq;
  private AtomicInteger deq;
  public int rem;
  private AtomicInteger size;
  Condition notEmpty;
  Condition notFull;



  public lock_free(int cap) {

    this.items = new Object[cap];
    this.head = new AtomicInteger(0);
    this.tail = new AtomicInteger(0);
    this.rem = rem;
    this.size = new AtomicInteger(0);

  }

  public void Enqueue(Object item) throws InterruptedException {

    while (size.get() >= rem) {
      Thread.sleep(1);
    } ;

    items[tail.getAndIncrement() % items.length] = item;
    size.getAndIncrement();

  }

  public Object Dequeue() throws InterruptedException {

    while (size.get() == 0) {
      Thread.sleep(1);
    } ;
    
    Object value;

    value = items[head.getAndIncrement() % items.length];
    size.getAndDecrement();

    if (this.head.get() == this.rem) {
      this.head.set(0);
    }


    return value;
  }
  
  
}
