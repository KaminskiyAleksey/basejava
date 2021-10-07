package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage; // = new ArrayStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume resume1 = new Resume(UUID_1);
    private static final Resume resume2 = new Resume(UUID_2);
    private static final Resume resume3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume r = new Resume(UUID_2);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume r = new Resume("uuid4");
        storage.update(r);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] r;
        r = storage.getAll();
        Assert.assertEquals(3, r.length);
        Assert.assertEquals(resume1, r[0]);
        Assert.assertEquals(resume2, r[1]);
        Assert.assertEquals(resume3, r[2]);
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("uuid4");
        storage.save(r);
        Assert.assertEquals(r, storage.get("uuid4"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveDuplicate() throws Exception {
        Resume r = new Resume(UUID_2);
        storage.save(r);
    }

    @Test(expected = StorageException.class)
    public void saveOutOfBounds() throws Exception {
        Resume r;
        try {

            for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                r = new Resume("guid" + i);
                storage.save(r);
            }
        } catch (StorageException e) {
            Assert.fail();
        }
        r = new Resume("guid" + AbstractArrayStorage.STORAGE_LIMIT);
        storage.save(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid4");
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}