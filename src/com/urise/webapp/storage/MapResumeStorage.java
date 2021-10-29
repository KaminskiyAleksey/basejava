package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private Map<String, Resume> storageMap = new HashMap<>();

    protected Resume getSearchKey(String uuid) {
        return storageMap.get(uuid);
    }

    void doUpdate(Resume r, Resume resume) {
        storageMap.put(r.getUuid(), r);
    }

    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    void doSave(Resume r, Resume resume) {
        storageMap.put(r.getUuid(), r);
    }

    Resume doGet(Resume resume) {
        return resume;
    }

    void doDelete(Resume resume) {
        storageMap.remove(resume.getUuid());
    }

    public List<Resume> doCopyAll(){
        return new ArrayList<>(storageMap.values());
    }

    public int size() {
        return storageMap.size();
    }

    public void clear() {
        storageMap.clear();
    }
}


