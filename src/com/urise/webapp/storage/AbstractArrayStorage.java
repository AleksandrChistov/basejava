package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    private final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Resume с uuid '%s' не существует%n", uuid);
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.printf("Resume с uuid '%s' не существует%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Хранилище данных переполнено");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            insertElement(resume, index);
            size++;
        } else {
            System.out.printf("Resume с uuid '%s' уже существует%n", resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        } else {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public int size() {
        return size;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);

    protected abstract int findIndex(String uuid);
}
