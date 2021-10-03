package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        /*
            index = -insertPos -1 где insertPos индекс в массиве куда нужно вставить новый объект
            insertPos = - index - 1
            параметры arraycopy
               src - массив источник
               srcPos - индекс с которого копируем из источника
               dest - массив приемник
               destPos - индекс куда вставляем в приемник
               length - сколько элементов копируем
             */
        int insertPos = -index - 1;
        System.arraycopy(storage, insertPos, storage, insertPos + 1, size - insertPos);
        storage[insertPos] = r;
    }

    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}
