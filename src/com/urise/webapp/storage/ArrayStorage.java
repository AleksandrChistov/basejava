package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage {
    private final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.printf("Resume с uuid '%s' не существует%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Хранилище данных переполнено");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            storage[size] = resume;
            size++;
        } else {
            System.out.printf("Resume с uuid '%s' уже существует%n", resume.getUuid());
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        } else {
            size--;
            storage[index] = storage[size];
            storage[size] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
