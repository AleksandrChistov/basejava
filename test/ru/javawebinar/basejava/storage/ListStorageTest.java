package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListStorageTest extends AbstractStorageTest {
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
    public void isEmpty() {
        storage.clear();
        assertTrue(((ListStorage) storage).isEmpty());
    }
}