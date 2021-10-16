package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private Map<String, Resume> storageMap = new HashMap<>();

    protected Resume getKey(String uuid) {
        return storageMap.get(uuid);
    }

    void updateResume(Resume r, Object resume) {
        storageMap.put(r.getUuid(), r);
    }

    protected boolean isExist(Object resume) {
        return resume != null;
    }

    void saveResume(Resume r, Object resume) {
        storageMap.put(r.getUuid(), r);
    }

    Resume getResume(Object resume) {
        return (Resume) resume;
    }

    void deleteResume(Object resume) {
        storageMap.remove(((Resume)resume).getUuid());
    }

    public List<Resume> copyAll(){
        return new ArrayList<>(storageMap.values());
    }

    public int size() {
        return storageMap.size();
    }

    public void clear() {
        storageMap.clear();
    }
}


