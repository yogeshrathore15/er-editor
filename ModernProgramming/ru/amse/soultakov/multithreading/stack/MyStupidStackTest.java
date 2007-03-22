/*
 * Created on 04.12.2006
 */
package ru.amse.soultakov.multithreading.stack;

public class MyStupidStackTest {

    public static void main(String[] args) throws InterruptedException {
        final Stack<Integer> s = new Stack<Integer>(10);
        new Thread(new Runnable() {
            public void run() {
                try {
                    s.push(0);
                    s.push(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    System.out.println(s.pop() + " !");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        
        final class MyRunnable implements Runnable {
            
            private volatile boolean stopFlag = false;
            
            public void stop() {
                stopFlag = true;
            }
            
            public void run() {
                try {
                    while(!stopFlag) {
                            Thread.sleep(1000);
                            System.out.println("size " + s.getSize());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        MyRunnable r = new MyRunnable();
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
        System.out.println(s.pop());
        System.out.println(s.peek());
        for(int i = 0; i < 10; i++) {
            s.push(i);
        }
        System.out.println(s.pop());
        Thread.sleep(2000);
        System.out.println(s.pop());
        Thread.sleep(2000);
        System.out.println(s.pop());
        Thread.sleep(2000);
        System.out.println(s.pop());
        Thread.sleep(5000);
        r.stop();
        t.join();
        System.out.println("final size = " + s.getSize());
    }
    
}
