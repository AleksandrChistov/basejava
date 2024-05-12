/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int _size = 0;

    void clear() {
        for (int i = 0; i < _size; i++) {
            storage[i] = null;
        }
        _size = 0;
    }

    void save(Resume r) {
        storage[_size] = r;
        _size += 1;
    }

    Resume get(String uuid) {
        for (int i = 0; i < _size; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < _size; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                storage[i] = storage[_size - 1];
                storage[_size - 1] = null;
                _size -= 1;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[_size];
        System.arraycopy(storage, 0, resumes, 0, _size);
        return resumes;
    }

    int size() {
        if (_size != 0) {
            return _size;
        }

        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                _size = i;
                return _size;
            }
        }

        return storage.length;
    }
}
