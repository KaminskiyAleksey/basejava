package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.fillResume;

public abstract class AbstractStorageTest {
    protected Storage storage;

    protected static final File STORAGE_DIR = new File("C:\\Programs\\Java\\basejava\\storage");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String FIO_1 = "fio1";
    private static final String FIO_2 = "fio2";
    private static final String FIO_3 = "fio3";
    private static final String FIO_4 = "fio4";

    private static final Resume resume1;
    private static final Resume resume2;
    private static final Resume resume3;
    private static final Resume resume4;

    static {
        resume1 = fillResume(UUID_1, FIO_1);
        resume2 = fillResume(UUID_2, FIO_2);
        resume3 = fillResume(UUID_3, FIO_3);
        resume4 = fillResume(UUID_4, FIO_4);
    }

    public AbstractStorageTest(Storage storage) {
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
        Resume r = fillResume(UUID_2, FIO_2);
        storage.update(r);
        Assert.assertEquals(r, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume r = fillResume(UUID_4, FIO_4);
        storage.update(r);
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> actualResumes = storage.getAllSorted();

        List<Resume> expectedResumes = Arrays.asList(resume1, resume2, resume3);

        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void save() throws Exception {
        Resume r = fillResume(UUID_4, FIO_4);
        storage.save(r);
        Assert.assertEquals(r, storage.get(UUID_4));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveDuplicate() throws Exception {
        Resume r = fillResume(UUID_2, FIO_2);
        storage.save(r);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
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