package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    public AbstractArrayStorage() {
        storage = new Resume[10000];
    }

    @Override
    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    final public void save(Resume resume) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            insertElement(resume, index);
            size++;
        } else {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    @Override
    final public void delete(String uuid) {
        super.delete(uuid);
    }

    protected abstract void insertElement(Resume resume, int index);
}
