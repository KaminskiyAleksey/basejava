package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume r) {
        Object key = getNotExistedSearchKey(r.getUuid());
        saveElement(r, key);
    }

    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        deleteElement(key);
    }

    public void update(Resume r) {
        Object key = getExistedSearchKey(r.getUuid());
        updateElement(r, key);
    }

    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return getElement(key);
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;

    }

    private Object getExistedSearchKey(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract Object getIndex(String uuid);

    abstract boolean isExist(Object index);

    abstract void saveElement(Resume r, Object key);

    abstract void deleteElement(Object key);

    abstract void updateElement(Resume r, Object key);

    abstract Resume getElement(Object key);
}
