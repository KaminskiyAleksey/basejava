package com.urise.webapp.storage;

import com.urise.webapp.storage.serialize.ObjectStreamSerialize;

public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerialize()));
    }
}
