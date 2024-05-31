package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class ListStorage extends AbstractStorage {
    private final int DEFAULT_CAPACITY = 10;

    public ListStorage() {
        storage = new Resume[DEFAULT_CAPACITY];
    }

    final public Resume get(int index) {
        if (index < 0 || index > size) {
            throw new NotExistStorageException("with index " + index);
        }
        return storage[index];
    }

    final public void update(int index, Resume resume) {
        if (index < 0 || index > size) {
            throw new NotExistStorageException("with index " + index);
        } else {
            storage[index] = resume;
        }
    }

    @Override
    final public void clear() {
        storage = new Resume[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    final public void save(Resume resume) {
        if (size == storage.length)
            storage = Arrays.copyOf(storage, size + (size >> 1));
        storage[size] = resume;
        size++;
    }

    final public void save(int index, Resume resume) {
        if (size == storage.length)
            storage = Arrays.copyOf(storage, size + (size >> 1));
        System.arraycopy(storage, index, storage, index + 1, size - 1 - index);
        storage[index] = resume;
        size++;
    }

    @Override
    final public void delete(String uuid) {
        super.delete(uuid);
        if (size < (storage.length - (storage.length >> 1)))
            storage = Arrays.copyOf(storage, (storage.length - (storage.length >> 1)));
    }

    final public void delete(int index) {
        if (index < 0 || index > size) {
            throw new NotExistStorageException("with index " + index);
        } else {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
            if (size < (storage.length - (storage.length >> 1)))
                storage = Arrays.copyOf(storage, (storage.length - (storage.length >> 1)));
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void fillDeletedElement(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }
}
