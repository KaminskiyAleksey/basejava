package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume r) {
        int key = getNotExistedSearchKey(r.getUuid());
        saveElement(r, key);
    }

    public void delete(String uuid) {
        int key = getExistedSearchKey(uuid);
        deleteElement(key);
    }

    public void update(Resume r) {
        int key = getExistedSearchKey(r.getUuid());
        updateElement(r, key);
    }

    public Resume get(String uuid) {
        int key = getExistedSearchKey(uuid);
        return getElement(key);
    }

    private int getNotExistedSearchKey(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private int getExistedSearchKey(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract int getIndex(String uuid);

    abstract void saveElement(Resume r, int key);

    abstract void deleteElement(int key);

    abstract void updateElement(Resume r, int key);

    abstract Resume getElement(int key);
}
