package ru.javawebinar.basejava;

import java.io.File;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainFile {
    private final static String FILE_PATH = "./src/ru/javawebinar/basejava";

    public static void main(String[] args) {
        final File file = new File(FILE_PATH);
        printDirectoryDeeply(file);
    }

    private static void printDirectoryDeeply(File dir) {
        final File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.printf("\t - File: %s\n", file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    printDirectoryDeeply(file);
                }
            }
        }
    }
}
