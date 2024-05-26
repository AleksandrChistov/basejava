package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
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

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
