package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    public List<Resume> copyAll(){
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    void saveResume(Resume r, Integer key) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        insertResume(r, key);
        size++;
    }

    void deleteResume(Integer key) {
        fillDeletedResume(key);
        storage[size - 1] = null;
        size--;
    }

    void updateResume(Resume r, Integer key) {
        storage[(int) key] = r;
    }

    Resume getResume(Integer key) {
        return storage[key];
    }

    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    abstract Integer getKey(String uuid);

    protected abstract void fillDeletedResume(Integer index);

    protected abstract void insertResume(Resume r, Integer index);


}