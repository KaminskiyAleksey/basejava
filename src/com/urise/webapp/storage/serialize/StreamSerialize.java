package com.urise.webapp.storage.serialize;

import com.urise.webapp.model.Resume;
import java.io.*;

public interface StreamSerialize {
    void doWrite(Resume r, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException;
}
