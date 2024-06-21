package ru.javawebinar.basejava;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREAD_SIZE = 10_000;
    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private static int counter = 0;
    private static final SimpleDateFormat SDF = new SimpleDateFormat();
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {
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

        CountDownLatch latch = new CountDownLatch(THREAD_SIZE);
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ExecutorCompletionService<ExecutorService> completionService = new ExecutorCompletionService<>(executorService);
//        List<Thread> threads = new ArrayList<>(THREAD_SIZE);

        for (int i = 0; i < THREAD_SIZE; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    increaseCount();
//                    System.out.println("THREAD_LOCAL: " + THREAD_LOCAL.get().format(new Date()));
                    System.out.println("DTF: " + DTF.format(LocalDateTime.of(2024, 6, 21, 19, 33, 0)));
                }
                latch.countDown();
                return 5;
            });
            System.out.println(future.isDone());
            System.out.println(completionService.poll());
//            executorService.submit(() -> {
//                for (int j = 0; j < 100; j++) {
//                    increaseCount();
//                }
//                latch.countDown();
//            });
//            Thread thread = new Thread(() -> {
//                for (int j = 0; j < 100; j++) {
//                    increaseCount();
//                }
//                latch.countDown();
//            });
//            thread.start();
//            threads.add(thread);
        }

//        threads.forEach(thread -> {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        Thread.sleep(5000);
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        System.out.println("Counter: " + ATOMIC_INTEGER.get());
    }

    private static void increaseCount() {
        double sin = Math.sin(13);
//        lock.lock();
//        try {
            ATOMIC_INTEGER.incrementAndGet();
//            counter++;
//        } finally {
//            lock.unlock();
//        }
    }

//    private static void increaseCount() {
//        double sin = Math.sin(13);
//        synchronized (LOCK) {
//            counter++;
//        }
//    }
}
