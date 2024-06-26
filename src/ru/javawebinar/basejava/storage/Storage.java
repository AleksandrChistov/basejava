package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {
    void clear();
    void update(Resume resume);
    void save(Resume resume);
    Resume get(String key);
    void delete(String key);
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    List<Resume> getAllSorted();
    int size();
}
