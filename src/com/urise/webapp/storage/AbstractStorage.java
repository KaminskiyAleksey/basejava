package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    public List<Resume> getAllSorted() {
        List<Resume> list = copyAll();
        Collections.sort(list);
        return list;
    }

    public void save(Resume r) {
        Object key = getNotExistedSearchKey(r.getUuid());
        saveResume(r, key);
    }

    public void delete(String uuid) {
        Object key = getExistedSearchKey(uuid);
        deleteResume(key);
    }

    public void update(Resume r) {
        Object key = getExistedSearchKey(r.getUuid());
        updateResume(r, key);
    }

    public Resume get(String uuid) {
        Object key = getExistedSearchKey(uuid);
        return getResume(key);
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object index = getKey(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;

    }

    private Object getExistedSearchKey(String uuid) {
        Object index = getKey(uuid);
        if (!isExist(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    abstract List<Resume> copyAll();

    abstract Object getKey(String uuid);

    abstract boolean isExist(Object index);

    abstract void saveResume(Resume r, Object key);

    abstract void deleteResume(Object key);

    abstract void updateResume(Resume r, Object key);

    abstract Resume getResume(Object key);
}
