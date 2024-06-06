package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> expectedEmptyList = new ArrayList<>();
        assertIterableEquals(expectedEmptyList, storage.getAllSorted());
    }

    @Test()
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1, "Resume 1")));
    }

    @Test()
    public void saveStorageOverflow() {
        final int STORAGE_LENGTH = ((AbstractArrayStorage) storage).storage.length;
        storage.clear();
        for (int i = 0; i < STORAGE_LENGTH; i++) {
            storage.save(new Resume("uuid" + i, "Resume " + i));
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid" + STORAGE_LENGTH, "Resume " + STORAGE_LENGTH)));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        assertIterableEquals(expected, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertSize(INITIAL_SIZE);
    }
}