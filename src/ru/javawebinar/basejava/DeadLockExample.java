package ru.javawebinar.basejava;

public class DeadLockExample {
    private final static int TRIES = 100;
    private final static String CHEST = "Chest";
    private final static String KEY = "Key";

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = getThreadExample("Джек", CHEST, KEY);
        Thread thread2 = getThreadExample("Билл", KEY, CHEST);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Код не выполнится, случился Deadlock");
    }

    private static Thread getThreadExample(String name, String lock1, String lock2) {
        return new Thread(() -> {
            for (int i = 0; i < TRIES; i++) {
                synchronized (lock1) {
                    System.out.println(name + " захватил " + (lock1.equals(CHEST) ? "сундук" : "ключ"));
                    synchronized (lock2) {
                        System.out.println(name + " захватил " + (lock2.equals(KEY) ? "ключ" : "сундук"));
                        System.out.println(name + ": йо-хо-хой, сокровище теперь моё!");
                    }
                }
            }
        });
    }
}
