package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public void save(Resume r) {
        int key = checkNotExistedKey(r.getUuid());
        saveElement(r, key);
    }

    public void delete(String uuid) {
        int key = checkExistedKey(uuid);
        deleteElement(key);
    }

    public void update(Resume r) {
        int key = checkExistedKey(r.getUuid());
        updateElement(r, key);
    }

    public Resume get(String uuid) {
        int key = checkExistedKey(uuid);
        return getElement(key);
    }

    abstract int checkNotExistedKey(String uuid);

    abstract void saveElement(Resume r, int key);

    abstract int checkExistedKey(String uuid);

    abstract void deleteElement(int key);

    abstract void updateElement(Resume r, int key);

    abstract Resume getElement(int key);
}
