package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private List<Resume> storageList = new ArrayList<>();

    public int size() {
        return storageList.size();
    }

    public void clear() {
        storageList.clear();
    }

    public Resume[] getAll() {
        return storageList.toArray(new Resume[storageList.size()]);
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    void saveElement(Resume r, int key) {
        storageList.add(r);
    }

    void deleteElement(int key) {
        storageList.remove(key);
    }

    void updateElement(Resume r, int key) {
        storageList.set(key, r);
    }

    Resume getElement(int key) {
        return storageList.get(key);
    }
}
