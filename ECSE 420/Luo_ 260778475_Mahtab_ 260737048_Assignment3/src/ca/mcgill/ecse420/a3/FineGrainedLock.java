package ca.mcgill.ecse420.a3;

import java.util.LinkedList;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedLock<T> {
	public Node head = new Node();
	public Node tail = new Node();

	public boolean contains(T item) {
		Node previous = head;
		previous.lock();
		Node current = previous.next;
		current.lock();
		int key = item.hashCode();

		try {
			while (current.key <= key) {
				// check if the item has been found
				if (current.item == item) {
					return true;
				}
				// Hand over hand locking
				previous.unlock();
				previous = current;
				// end if the tail id reached
				if (current.next != tail) {
					current = current.next;
					current.lock();
				} else {
					current = current.next;
					current.lock();
					break;
				}
			}

		} finally {
			previous.unlock();
			current.unlock();
		}
		return false;
	}
	public boolean add (T item){
        int key = item.hashCode();
        head.lock();
        Node previous = head;
        try{
            Node current = previous.next;
            current.lock();
            try{
                while(current.key < key){
                    previous.unlock();
                    previous = current;
                    current = current.next;
                    current.lock();
                }
                if(current.key == key){
                    return false;
                }
                Node newNode = new Node();
                newNode.item = item;
                newNode.key = key;
                newNode.next = current;
                previous.next = newNode;
                return true;
            }
            finally{
                current.unlock();
            }

        }finally{
            previous.unlock();
        }
    }
	

}
