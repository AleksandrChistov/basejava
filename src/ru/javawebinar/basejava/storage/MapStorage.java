package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    final public void clear() {
        storage.clear();
    }

    @Override
    final protected void doSave(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    final protected Resume doGet(Object foundResume) {
        return (Resume) foundResume;
    }

    @Override
    final protected void doDelete(Object resume) {
        storage.remove(((Resume) resume).getUuid());
    }

    @Override
    final protected void doUpdate(Resume r, Object foundResume) {
        Resume resume = (Resume) foundResume;
        storage.put(resume.getUuid(), resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    final public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    final public int size() {
        return storage.size();
    }

    final public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    final protected Object getSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    final protected boolean isExist(Object resume) {
        return resume != null && storage.containsKey(((Resume) resume).getUuid());
    }
}
