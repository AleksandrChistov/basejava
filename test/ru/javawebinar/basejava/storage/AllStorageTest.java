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
        ObjectStreamFileStorageTest.class,
        ObjectStreamPathStorage.class,
})
public class AllStorageTest {
}
