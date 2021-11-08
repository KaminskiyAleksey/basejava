package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.urise.webapp.util.StringUtil.convertToEmptyIfNull;
import static com.urise.webapp.util.StringUtil.convertToNullIfEmpty;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(dos, r.getContacts().entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeWithException(dos, r.getSections().entrySet(), entry -> {
                SectionType sectionName = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(sectionName.name());
                switch (sectionName) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(dos, ((OrganizationSection) section).getOrganizations(), org -> {
                            Link link = org.getHomePage();
                            dos.writeUTF(convertToEmptyIfNull(link.getName()));
                            dos.writeUTF(convertToEmptyIfNull(link.getUrl()));
                            writeWithException(dos, org.getPositions(), pos -> {
                                writeDate(dos, pos.getStartDate());
                                writeDate(dos, pos.getEndDate());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(convertToEmptyIfNull(pos.getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readMapWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readMapWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSectionWithException(dis, sectionType));
            });
            return resume;
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, FunctionalElementWriter<T> functionalElementWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            functionalElementWriter.write(item);
        }
    }

    private void writeDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }

    private LocalDate readDate(DataInputStream dis) throws IOException {
        return LocalDate.of(dis.readInt(), dis.readInt(), 1);
    }

    private <T> void readMapWithException(DataInputStream dis, FunctionalElementReadMap reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.process();
        }
    }

    private Section readSectionWithException(DataInputStream dis, SectionType sectionName) throws IOException {
        switch (sectionName) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readListWithException(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new OrganizationSection(
                        readListWithException(dis, () -> new Organization(
                                new Link(convertToNullIfEmpty(dis.readUTF()), convertToNullIfEmpty(dis.readUTF())),
                                readListWithException(dis, () -> new Organization.Position(
                                        readDate(dis), readDate(dis), dis.readUTF(), convertToNullIfEmpty(dis.readUTF())
                                ))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readListWithException(DataInputStream dis, FunctionalElementReadList<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    @FunctionalInterface
    interface FunctionalElementWriter<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    private interface FunctionalElementReadMap {
        void process() throws IOException;
    }

    @FunctionalInterface
    interface FunctionalElementReadList<T> {
        T read() throws IOException;
    }
}
