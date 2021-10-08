package ca.mcgill.ecse420.a1;

public class Deadlock {

    public static Object resource1 = new Object();
    public static Object resource2 = new Object();
    public static void main(String[] args) {
        TaskClassA task_A = new TaskClassA();
        TaskClassB task_B = new TaskClassB();
        Thread thread_1 = new Thread(task_A);
        Thread thread_2 = new Thread(task_B);
        thread_1.start();
        thread_2.start();
    }

    
    
    
    
    
    
    
    
    public static class TaskClassA implements Runnable {
        public void run() {
            synchronized (resource1 ){
                System.out.println("Thread 1 - Got lock 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 1 - Waiting for lock 2");
                synchronized (resource2) {}
            }
        }
    }

    public static class TaskClassB implements Runnable {
        public void run() {
            synchronized (resource2){
                System.out.println("Thread 2 - Got lock 2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
                System.out.println("Thread 2 - Waiting for lock 1");
                synchronized (resource1) {}
            }
        }
    }
}
