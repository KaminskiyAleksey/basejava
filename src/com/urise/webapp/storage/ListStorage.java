package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> storageList = new ArrayList<>();

    public int size() {
        return storageList.size();
    }

    public void clear() {
        storageList.clear();
    }

    public List<Resume> copyAll(){
        return new ArrayList<>(storageList);
    }

    protected Integer getKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    void saveResume(Resume r, Integer key) {
        storageList.add(r);
    }

    void deleteResume(Integer key) {
        storageList.remove((int) key);
    }

    void updateResume(Resume r, Integer key) {
        storageList.set(key, r);
    }

    Resume getResume(Integer key) {
        return storageList.get(key);
    }
}
