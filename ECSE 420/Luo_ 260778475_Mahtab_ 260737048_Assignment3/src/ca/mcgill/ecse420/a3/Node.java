package ca.mcgill.ecse420.a3;
import java.util.concurrent.locks.ReentrantLock;


public class Node<T> {
	public ReentrantLock nodeLock = new ReentrantLock(); 
	public T item;
	public int key;
	public volatile Node next;
	public void lock() {
		this.nodeLock.lock();
	    System.out.println("the node = " + key + " is locked");
	}
	public void unlock() {
		System.out.println("the node = " + key + " is unlocked");
		this.nodeLock.unlock();
	}
}
