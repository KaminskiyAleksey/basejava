package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> arrayList = new ArrayList();

    public int size() {
        return arrayList.size();
    }

    public void clear() {
        arrayList.clear();
    }

    public Resume[] getAll() {
        return arrayList.toArray(new Resume[arrayList.size()]);
    }

    int checkNotExistedKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUuid().equals(uuid)) {
                throw new ExistStorageException(uuid);
            }
        }
        return 0;
    }

    int checkExistedKey(String uuid) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    void saveElement(Resume r, int key) {
        arrayList.add(r);
    }

    void deleteElement(int key) {
        arrayList.remove(key);
    }

    void updateElement(Resume r, int key) {
        arrayList.set(key, r);
    }

    Resume getElement(int key) {
        return arrayList.get(key);
    }
}
