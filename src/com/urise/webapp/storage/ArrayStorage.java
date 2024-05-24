package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = this.findIndexByUuid(resume.getUuid());
        if (index == -1) {
            System.out.printf("Resume с uuid '%s' не существует%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Хранилище данных переполнено");
        } else {
            int index = this.findIndexByUuid(resume.getUuid());
            if (index == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.printf("Resume с uuid '%s' уже существует%n", resume.getUuid());
            }
        }
    }

    public Resume get(String uuid) {
        int index = this.findIndexByUuid(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = this.findIndexByUuid(uuid);
        if (index == -1) {
            System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
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

    private int findIndexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
