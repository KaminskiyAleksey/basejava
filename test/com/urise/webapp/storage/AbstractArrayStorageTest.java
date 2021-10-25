package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.ResumeTestData.fillResume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOutOfBounds() throws Exception {
        Resume r;
        try {
            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                r = new Resume("uuid" + i + 1, "Fio" + i + 1);
                storage.save(r);
            }
        } catch (StorageException e) {
            Assert.fail("Произошло переполнение раньше ожидаемого");
        }
        r = fillResume("uuid" + AbstractArrayStorage.STORAGE_LIMIT, "Fio" + AbstractArrayStorage.STORAGE_LIMIT);
        storage.save(r);
    }

}