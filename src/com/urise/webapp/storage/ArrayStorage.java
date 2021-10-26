package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected void fillDeletedResume(int index) {
        storage[index] = storage[size - 1];
    }

    protected void insertResume(Resume r, int index) {
        storage[size] = r;
    }

    protected Integer getKey(String uuid) {
        for (Integer i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}