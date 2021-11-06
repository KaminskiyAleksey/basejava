package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.util.StringUtil.*;

public class DataStreamSerializer implements StreamSerializer {
    @FunctionalInterface
    interface FunctionalElement<T> {
        void write(T t) throws IOException;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            /*dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }*/

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeSection(entry.getKey(), entry.getValue(), dos);
            }
        }
    }

    private <T> void writeWithException (DataOutputStream dos, Collection<T> collection, FunctionalElement<T> functionalElement) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            functionalElement.write(item);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSection = dis.readInt();
            Section section;
            for (int i = 0; i < sizeSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                section = readSection(sectionType, dis);
                resume.addSection(sectionType, section);
            }
            return resume;
        }
    }

    private void writeSection(SectionType sectionName, Section section, DataOutputStream dos) throws IOException {
        switch (sectionName) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> items = ((ListSection) section).getItems();
                dos.writeInt(items.size());
                for (String str : ((ListSection) section).getItems()) {
                    dos.writeUTF(str);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationItems = ((OrganizationSection) section).getOrganizations();
                dos.writeInt(organizationItems.size());
                for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                    writeOrganization(organization, dos);
                }
                break;
        }
    }

    private void writeOrganization(Organization organization, DataOutputStream dos) throws IOException {
        dos.writeUTF(convertToEmptyIfNull(organization.getHomePage().getName()));
        dos.writeUTF(convertToEmptyIfNull(organization.getHomePage().getUrl()));
        List<Organization.Position> positions = organization.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position position : organization.getPositions()) {
            writePosition(position, dos);
        }
    }

    private void writePosition(Organization.Position position, DataOutputStream dos) throws IOException {
        dos.writeInt(position.getStartDate().getYear());
        dos.writeInt(position.getStartDate().getMonthValue());
        dos.writeInt(position.getEndDate().getYear());
        dos.writeInt(position.getEndDate().getMonthValue());
        dos.writeUTF(position.getTitle());
        dos.writeUTF(convertToEmptyIfNull(position.getDescription()));
    }

    private Section readSection(SectionType sectionName, DataInputStream dis) throws IOException {
        int sectionSize;
        switch (sectionName) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> listItems = new ArrayList<>();
                sectionSize = dis.readInt();
                for (int i = 0; i < sectionSize; i++) {
                    listItems.add(dis.readUTF());
                }
                return new ListSection(listItems);
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationItems = new ArrayList<>();
                sectionSize = dis.readInt();
                for (int i = 0; i < sectionSize; i++) {
                    organizationItems.add(readOrganization(dis));
                }
                return new OrganizationSection(organizationItems);
            default:
                throw new IllegalStateException();
        }
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        String name = convertToNullIfEmpty(dis.readUTF());
        String url = convertToNullIfEmpty(dis.readUTF());

        Link link = new Link(name, url);
        int positionSize = dis.readInt();

        for (int i = 0; i < positionSize; i++) {
            positions.add(readPosition(dis));
        }
        Organization organization = new Organization(link, positions);
        return organization;
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        int dateYear = dis.readInt();
        int dateMonth = dis.readInt();
        LocalDate startDate = LocalDate.of(dateYear,dateMonth,1);
        dateYear = dis.readInt();
        dateMonth = dis.readInt();
        LocalDate endDate = LocalDate.of(dateYear,dateMonth,1);
        String title = dis.readUTF();
        String description = convertToNullIfEmpty(dis.readUTF());
        return new Organization.Position(startDate, endDate, title, description);
    }
}
