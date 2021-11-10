package com.urise.webapp;

public class MainDeadlock {
    public static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            printThreadInfo(" wait object " + lock1);
            synchronized (lock1) {
                printThreadInfo(" capture object " + lock1);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                printThreadInfo(" wait object " + lock2);
                synchronized (lock2) {
                    printThreadInfo(" capture object " + lock2);
                }
            }
        }).start();
    }

    private static void printThreadInfo(String message) {
        System.out.println(Thread.currentThread().getName() + message);
    }
}