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

    public List<Resume> doCopyAll(){
        return new ArrayList<>(storageList);
    }

    protected Integer getSearchKey(String uuid) {
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

    void doSave(Resume r, Integer key) {
        storageList.add(r);
    }

    void doDelete(Integer key) {
        storageList.remove((int) key);
    }

    void doUpdate(Resume r, Integer key) {
        storageList.set(key, r);
    }

    Resume doGet(Integer key) {
        return storageList.get(key);
    }
}
