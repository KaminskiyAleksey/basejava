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

    protected Object getKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Object index) {
        return (int)index >= 0;
    }

    void saveResume(Resume r, Object key) {
        storageList.add(r);
    }

    void deleteResume(Object key) {
        storageList.remove((int) key);
    }

    void updateResume(Resume r, Object key) {
        storageList.set((int) key, r);
    }

    Resume getResume(Object key) {
        return storageList.get((int) key);
    }
}
