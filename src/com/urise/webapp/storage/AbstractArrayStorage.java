package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    void saveElement(Resume r, Object key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertElement(r, (int) key);
        size++;
    }

    void deleteElement(Object key) {
        fillDeletedElement((int) key);
        storage[size - 1] = null;
        size--;
    }

    void updateElement(Resume r, Object key) {
        storage[(int) key] = r;
    }

    Resume getElement(Object key) {
        return storage[(int) key];
    }

    protected boolean isExist(Object index) {
        if ((int) index >= 0) {
            return true;
        }
        return false;
    }

    abstract Object getIndex(String uuid);

    protected abstract void fillDeletedElement(int index);

    protected abstract void insertElement(Resume r, int index);


}