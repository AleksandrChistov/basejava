package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for sorted Resumes
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertElement(Resume resume, Integer index) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    protected void fillDeletedElement(Integer index) {
        int countToBeMoved = size - index - 1;
        if (countToBeMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, countToBeMoved);
        }
    }
}
