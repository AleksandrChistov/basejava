package ru.javawebinar.basejava.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ListStorageTest.class,
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        MapUuidStorageTest.class,
        MapResumeStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class
})
public class AllStorageTest {
}
