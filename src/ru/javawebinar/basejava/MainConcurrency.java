package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREAD_SIZE = 10_000;
    private static int counter = 0;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();

        System.out.println(thread0.getState());

        List<Thread> threads = new ArrayList<>(THREAD_SIZE);

        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < THREAD_SIZE; j++) {
                    increaseCount();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
//        Thread.sleep(5000);
        System.out.println("Counter: " + counter);
    }

    private static void increaseCount() {
        double sin = Math.sin(13);
        synchronized (LOCK) {
            counter++;
        }
    }

    private static class DeadLockExample {
        private final static int TRIES = 100;

        public static void main(String[] args) throws InterruptedException {
            final Object chest = new Object();
            final Object key = new Object();

            Thread thread1 = new Thread(() -> {
                for (int i = 0; i < TRIES; i++) {
                    synchronized (chest) {
                        System.out.println("Джек захватил сундук");
                        synchronized (key) {
                            System.out.println("Джек захватил ключ");
                            System.out.println("Джек: йо-хо-хой, сокровище теперь моё!");
                        }
                    }
                }
            });

            Thread thread2 = new Thread(() -> {
                for (int i = 0; i < TRIES; i++) {
                    synchronized (key) {
                        System.out.println("Билл захватил ключ");
                        synchronized (chest) {
                            System.out.println("Билл захватил сундук");
                            System.out.println("Билл: йо-хо-хой, сокровище теперь моё!");
                        }
                    }
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            System.out.println("Код не выполнится, случился Deadlock");
        }
    }
}
