package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamSerialize;

public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerialize()));
    }
}