package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = copyAll();
        Collections.sort(list);
        return list;
    }

    public void save(Resume r) {
        LOG.info("Save " + r);
        SK key = getNotExistedSearchKey(r.getUuid());
        saveResume(r, key);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getExistedSearchKey(uuid);
        deleteResume(key);
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SK key = getExistedSearchKey(r.getUuid());
        updateResume(r, key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getExistedSearchKey(uuid);
        return getResume(key);
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    private SK getExistedSearchKey(String uuid) {
        SK key = getKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    abstract List<Resume> copyAll();

    abstract SK getKey(String uuid);

    abstract boolean isExist(SK key);

    abstract void saveResume(Resume r, SK key);

    abstract void deleteResume(SK key);

    abstract void updateResume(Resume r, SK key);

    abstract Resume getResume(SK key);
}
