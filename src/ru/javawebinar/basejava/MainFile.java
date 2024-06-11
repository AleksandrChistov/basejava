package ru.javawebinar.basejava;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Interactive test for ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainFile {
    private final static String FILE_PATH = "./src/ru/javawebinar/basejava/";

    public static void main(String[] args) {
        final File file = new File(FILE_PATH);
        System.out.println(findFiles(file));
    }

    private static List<String> findFiles(File dir) {
        final List<String> files = new ArrayList<>();
        if (dir == null) {
            return files;
        }
        for (File filename : dir.listFiles()) {
            if (filename.isDirectory()) {
                files.addAll(findFiles(filename));
            } else {
                files.add(filename.getName());
            }
        }
        return files;
    }
}
