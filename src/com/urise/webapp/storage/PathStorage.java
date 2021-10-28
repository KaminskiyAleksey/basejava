package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public abstract class PathStorage extends AbstractStorage<Path> {
    private Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

    protected PathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path getKey(String uuid) {
        return null;// new Path(directory, uuid);
    }

    @Override
    protected void updateResume(Resume r, Path Path) {
        /*try {
            //doWrite(r, new BufferedOutputStream(new PathOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }*/
    }

    @Override
    protected boolean isExist(Path Path) {
        return true; //Path.exists();
    }

    @Override
    protected void saveResume(Resume r, Path Path) {
        /*try {
            Path.createNewPath();
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + Path.getAbsolutePath(), Path.getName(), e);
        }*/
        updateResume(r, Path);
    }

    @Override
    protected Resume getResume(Path Path) {
        /*try {
            return doRead(new BufferedInputStream(new PathInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", Path.getName(), e);
        }*/
        return null;
    }

    @Override
    protected void deleteResume(Path Path) {
        /*if (!Path.delete()) {
            throw new StorageException("Path delete error", Path.getName());
        }*/
    }

    @Override
    protected List<Resume> copyAll() {
        /*Path[] Paths = directory.listPaths();
        if (Paths == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(Paths.length);
        for (Path Path : Paths) {
            list.add(getResume(Path));
        }
        return list;*/
        return null;
    }
}