package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.ResumeTestData;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("./storage");
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final Resume RESUME_1 = ResumeTestData.createResume(UUID_1, "Resume 1");
    protected static final Resume RESUME_2 = ResumeTestData.createResume(UUID_2, "Resume 2");
    protected static final Resume RESUME_3 = ResumeTestData.createResume(UUID_3, "Resume 3");
    protected static final Resume RESUME_4 = ResumeTestData.createResume(UUID_4, "Resume 4");
    protected static final int INITIAL_SIZE = 3;
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    public void get() {
        assertGet(RESUME_2);
    }

    @Test
    public final void save() {
        storage.save(RESUME_4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    public final void delete() {
        storage.delete(UUID_2);
        assertSize(INITIAL_SIZE -1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void update() {
        storage.update(RESUME_2);
        assertEquals(RESUME_2, storage.get(UUID_2));
    }

    @Test()
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test()
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("dummy", "Dummy resume")));
    }

    @Test()
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    protected void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    protected void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}