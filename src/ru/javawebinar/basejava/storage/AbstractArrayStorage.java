package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void doSave(Resume resume, Object key) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        insertElement(resume, (int) key);
        size++;
    }

    @Override
    protected final Resume doGet(Object key) {
        return storage[(int) key];
    }

    @Override
    protected final void doDelete(Object key) {
        fillDeletedElement((int) key);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final void doUpdate(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    @Override
    protected final boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public final int size() {
        return size;
    }

    protected abstract void insertElement(Resume resume, int index);

    protected abstract void fillDeletedElement(int index);
}
