package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<Resume, String> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume resume, Object key) {
        storage.put(resume, resume.getFullName());
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doDelete(Object resume) {
        storage.remove((Resume) resume);
    }

    @Override
    protected void doUpdate(Resume r, Object foundResume) {
        Resume resume = (Resume) foundResume;
        storage.put(resume, resume.getFullName());
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.keySet());
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume resume = null; // Инициализируем resume как null
        for (Resume key : storage.keySet()) {
            if (key.getUuid().equals(uuid)) { // Проверяем, совпадает ли uuid объекта Resume с искомым uuid
                resume = key; // Сохраняем найденный объект Resume
                break; // Выходим из цикла, так как мы нашли нужный Resume
            }
        }
        return resume;
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null && storage.containsKey((Resume) resume);
    }
}
