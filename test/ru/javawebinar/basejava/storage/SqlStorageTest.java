package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static final SqlStorage SQL_STORAGE = new SqlStorage(
            Config.get().getDbUrl(),
            Config.get().getDbUser(),
            Config.get().getDbPassword()
    );
    public SqlStorageTest() {
        super(SQL_STORAGE);
    }
}