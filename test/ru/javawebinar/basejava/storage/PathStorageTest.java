package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.streams.ObjectStreamStorage;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getName(), new ObjectStreamStorage()));
    }
}