package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final AbstractArrayStorage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void get() {
        assertEquals(new Resume(UUID_2), storage.get(UUID_2));
    }

    @Test
    void update() {
        Resume savedResume = new Resume("uuid4");
        Resume updatedResume = new Resume("uuid4");

        storage.save(savedResume);
        storage.update(updatedResume);

        assertNotSame(savedResume, updatedResume);
        assertEquals(savedResume, updatedResume);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void getAll() {
        assertEquals(storage.size(), storage.getAll().length);
    }

    @Test
    void save() {
        int oldSize = storage.size();
        storage.save(new Resume("uuid4"));
        assertEquals(oldSize + 1, storage.size());
    }

    @Test
    void delete() {
        int oldSize = storage.size();
        storage.delete(UUID_2);
        assertEquals(oldSize - 1, storage.size());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test()
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test()
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy")));
    }

    @Test()
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test()
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test()
    public void saveStorageOverflow() {
        final int STORAGE_LENGTH = storage.storage.length;
        try {
            for (int i = storage.size() + 1; i <= STORAGE_LENGTH; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("Ahead of time stack overflow");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid" + STORAGE_LENGTH + 1)));
    }
}