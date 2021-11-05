package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.util.NullDataString.*;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                writeSection(entry.getKey(), entry.getValue(), dos);
            }
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
            // TODO implements sections
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
        dos.writeUTF(writeNull(organization.getHomePage().getName()));
        dos.writeUTF(writeNull(organization.getHomePage().getUrl()));
        List<Organization.Position> positions = organization.getPositions();
        dos.writeInt(positions.size());
        for (Organization.Position position : organization.getPositions()) {
            writePosition(position, dos);
        }
    }

    private void writePosition(Organization.Position position, DataOutputStream dos) throws IOException {
        dos.writeUTF(position.getStartDate().toString());
        dos.writeUTF(position.getEndDate().toString());
        dos.writeUTF(position.getTitle());
        dos.writeUTF(writeNull(position.getDescription()));
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
        String name = readNull(dis.readUTF());
        String url = readNull(dis.readUTF());

        Link link = new Link(name, url);
        int positionSize = dis.readInt();

        for (int i = 0; i < positionSize; i++) {
            positions.add(readPosition(dis));
        }
        Organization organization = new Organization(link, positions);
        return organization;
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        LocalDate startDate = LocalDate.parse(dis.readUTF());
        LocalDate endDate = LocalDate.parse(dis.readUTF());
        String title = dis.readUTF();
        String description = readNull(dis.readUTF());
        return new Organization.Position(startDate, endDate, title, description);
    }
}
