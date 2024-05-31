package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest<AbstractArrayStorage> {
    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] expectedEmptyArray = new Resume[]{};
        assertArrayEquals(expectedEmptyArray, storage.getAll());
    }

    @Test()
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test()
    public void saveStorageOverflow() {
        final int STORAGE_LENGTH = storage.storage.length;
        storage.clear();
        for (int i = 0; i < STORAGE_LENGTH; i++) {
            storage.save(new Resume("uuid" + i));
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid" + STORAGE_LENGTH)));
    }
}