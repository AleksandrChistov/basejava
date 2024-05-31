package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

class ListStorageTest extends AbstractStorageTest<ListStorage>{
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] expectedEmptyArray = new Resume[]{};
        assertArrayEquals(expectedEmptyArray, storage.getAll());
    }

    @Test
    public void saveWithIndex() {
        storage.save(1, RESUME_4);
        assertEquals(storage.storage[1], RESUME_4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public void deleteByIndex() {
        storage.delete(0);
        assertSize(INITIAL_SIZE - 1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void updateWithIndex() {
        storage.update(0, RESUME_2);
        assertSame(RESUME_2, storage.get(0));
    }

    @Test
    public void isEmpty() {
        storage.clear();
        assertTrue(storage.isEmpty());
    }
}