/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        storage[size()] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].uuid == uuid) {
                storage[i] = storage[size() - 1];
                storage[size() - 1] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageNew = new Resume[size()];
        for (int i = 0; i < size(); i++) {
            storageNew[i] = storage[i];
        }
        return storageNew;
    }

    int size() {
        int arraySize = 0;
        while ((storage[arraySize] != null) && (arraySize < storage.length)) {
            arraySize++;
        }
        return arraySize;
    }
}
