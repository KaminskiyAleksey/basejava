package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    private Map<String, Resume> storageMap = new HashMap<>();

    public int size() {
        return storageMap.size();
    }

    public void clear() {
        storageMap.clear();
    }

    public List<Resume> copyAll(){
        return new ArrayList<>(storageMap.values());
    }

    protected Object getKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(Object index) {
        return storageMap.containsKey(index.toString());
    }

    void saveResume(Resume r, Object key) {
        storageMap.put((String) key, r);
    }

    void deleteResume(Object key) {
        storageMap.remove((String) key);
    }

    void updateResume(Resume r, Object key) {
        storageMap.put((String) key, r);
    }

    Resume getResume(Object key) {
        return storageMap.get((String) key);
    }
}
