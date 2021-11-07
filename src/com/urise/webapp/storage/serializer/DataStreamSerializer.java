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
    @FunctionalInterface
    interface FunctionalElementWriter<T> {
        void write(T t) throws IOException;
    }

    private interface FunctionalElementReadMap {
        void process() throws IOException;
    }

    @FunctionalInterface
    interface FunctionalElementReadList<T> {
        T read() throws IOException;
    }

    public static void WriteDate(DataOutputStream dos, LocalDate date) throws IOException {
        dos.writeInt(date.getYear());
        dos.writeInt(date.getMonthValue());
    }
    
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
                            dos.writeUTF(convertToEmptyIfNull(org.getHomePage().getName()));
                            dos.writeUTF(convertToEmptyIfNull(org.getHomePage().getUrl()));
                            writeWithException(dos, org.getPositions(), pos -> {
                                WriteDate(dos, pos.getStartDate());
                                WriteDate(dos, pos.getEndDate());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(convertToEmptyIfNull(pos.getDescription()));
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, FunctionalElementWriter<T> functionalElementWriter) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            functionalElementWriter.write(item);
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
                                        LocalDate.of(dis.readInt(), dis.readInt(), 1), LocalDate.of(dis.readInt(), dis.readInt(), 1), dis.readUTF(), convertToNullIfEmpty(dis.readUTF())
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
}
