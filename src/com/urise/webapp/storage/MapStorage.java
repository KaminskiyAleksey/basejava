package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private Map<String, Resume> storageMap = new HashMap<>();

    public int size() {
        return storageMap.size();
    }

    public void clear() {
        storageMap.clear();
    }

    public Resume[] getAll() {
        Resume[] resumes = new Resume[size()];
        int index = 0;
        for (Map.Entry<String, Resume> entry : storageMap.entrySet()) {
            resumes[index] = entry.getValue();
            index++;
        }
        return resumes;
    }

    protected Object getIndex(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object index) {
        return storageMap.containsKey(index.toString());
    }

    void saveElement(Resume r, Object key) {
        storageMap.put((String) key, r);
    }

    void deleteElement(Object key) {
        storageMap.remove((String) key);
    }

    void updateElement(Resume r, Object key) {
        storageMap.put((String) key, r);
    }

    Resume getElement(Object key) {
        return storageMap.get((String) key);
    }
}
