package ca.mcgill.ecse420.a2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.lang.*;

class Filter implements Lock{
  private AtomicInteger[] level;
  private AtomicInteger[] victim;
  private final int n;
  public Filter(int n) {
    // initialize
    this.n = n;
    level = new AtomicInteger[n];
    victim = new AtomicInteger[n];
    for (int i = 0; i < n; i++) {
      level[i] = new AtomicInteger();
      victim[i] = new AtomicInteger();
    }
  }
  @Override
    public void lock() {
        for (int i = 1; i < n; i++) { 
            level[(int) Thread.currentThread().getId()] = new AtomicInteger(i);
            victim[i] = new AtomicInteger((int)Thread.currentThread().getId());
            for(int k = 0; k < n ; k++)
                while ((k != (int)Thread.currentThread().getId()) && (level[k].get() >= i && victim[i].get() == (int)Thread.currentThread().getId()));

        }
    }
    @Override
    public void unlock() {
        int threadID = Integer.parseInt(Thread.currentThread().getName().split("-")[1]);
        level[threadID] = new AtomicInteger();
    }
    @Override
    public boolean tryLock() {
        return false;
    }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }
    @Override
    public void lockInterruptibly() throws InterruptedException {

    }
    @Override
    public java.util.concurrent.locks.Condition newCondition() {
        return null;
    }



}

