package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for sorted Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    public void save(Resume resume) {
        if (size == storage.length) {
            System.out.println("Хранилище данных переполнено");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.printf("Resume с uuid '%s' уже существует%n", resume.getUuid());
        } else {
            int insertIndex = -index - 1;
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.printf("Resume с uuid '%s' не существует%n", uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
        }
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
