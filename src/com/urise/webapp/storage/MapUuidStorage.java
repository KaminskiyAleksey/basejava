package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
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

    protected String getKey(String uuid) {
        return uuid;
    }

    protected boolean isExist(String key) {
        return storageMap.containsKey(key.toString());
    }

    void saveResume(Resume r, String key) {
        storageMap.put(key, r);
    }

    void deleteResume(String key) {
        storageMap.remove(key);
    }

    void updateResume(Resume r, String key) {
        storageMap.put( key, r);
    }

    Resume getResume(String key) {
        return storageMap.get(key);
    }
}
