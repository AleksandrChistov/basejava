package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final Resume[] storage = new Resume[10000];
    protected int size = 0;

    @Override
    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    final protected void doSave(Resume resume, Object key) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, (int) key);
        size++;
    }

    @Override
    final protected Resume doGet(Object key) {
        return storage[(int) key];
    }

    @Override
    final protected void doDelete(Object key) {
        fillDeletedElement((int) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    final protected void doUpdate(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    @Override
    final protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    final public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    final public int size() {
        return size;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
