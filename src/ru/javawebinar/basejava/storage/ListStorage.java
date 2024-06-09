package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume resume, Integer key) {
        if (key < 0) {
            storage.add(resume);
        } else {
            storage.add(key, resume);
        }
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected void doDelete(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    protected void doUpdate(Resume resume, Integer key) {
        storage.set(key, resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0 && index < storage.size();
    }
}
