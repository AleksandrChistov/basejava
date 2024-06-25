package ru.javawebinar.basejava.utils;

public class LazySingleton {

    private LazySingleton() {}

    // Initialization on demand holder
    private static final class InstanceHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

    public static LazySingleton getInstance() {
        return InstanceHolder.INSTANCE;
    }

    // Double-checked locking
//    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
