package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapResumeStorageTest extends AbstractStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        List<Resume> expectedEmptyList = new ArrayList<>();
        assertIterableEquals(expectedEmptyList, storage.getAllSorted());
    }

    @Test
    public void isEmpty() {
        storage.clear();
        assertTrue(((MapResumeStorage) storage).isEmpty());
    }
}