package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    final public void clear() {
        storage.clear();
    }

    @Override
    final protected void doSave(Resume resume, Object key) {
        if ((int) key < 0) {
            storage.add(resume);
        } else {
            storage.add((int) key, resume);
        }
    }

    @Override
    final protected Resume doGet(Object key) {
        return storage.get((int) key);
    }

    @Override
    final protected void doDelete(Object key) {
        storage.remove((int) key);
    }

    @Override
    final protected void doUpdate(Resume resume, Object key) {
        storage.set((int) key, resume);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    final public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

    @Override
    final public int size() {
        return storage.size();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    final protected Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    final protected boolean isExist(Object searchKey) {
        int index = (int) searchKey;
        return index >= 0 && index < storage.size();
    }
}
