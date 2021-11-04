package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.AbstractStorage;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DataStreamSerializer implements StreamSerializer {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

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
                resume.addSection(sectionType,  section);
            }
            return resume;
        }
    }

    private void writeSection(SectionType sectionName, Section section, DataOutputStream dos) throws IOException {
        LOG.info("write Section = " + sectionName);
        switch (sectionName) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) section).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> items = ((ListSection) section).getItems();
                dos.write(items.size());
                LOG.info("size = " + items.size());
                for (String str : ((ListSection) section).getItems()) {
                    dos.writeUTF(str);
                }
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationItems = ((OrganizationSection) section).getOrganizations();
                dos.write(organizationItems.size());
                LOG.info("size = " + organizationItems.size());
                for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                    writeOrganization(organization, dos);
                }
                break;
        }
    }

    private void writeOrganization(Organization organization , DataOutputStream dos) throws IOException {
        dos.writeUTF(organization.getHomePage().getName());
        dos.writeUTF(organization.getHomePage().getUrl());
        List<Organization.Position> positions = organization.getPositions();
        dos.write(positions.size());
        for (Organization.Position position : organization.getPositions()) {
            writePosition(position, dos);
        }
    }

    private void writePosition(Organization.Position position , DataOutputStream dos) throws IOException {
        dos.writeUTF(position.getStartDate().toString());
        dos.writeUTF(position.getEndDate().toString());
        dos.writeUTF(position.getTitle());
        dos.writeUTF(position.getDescription());
    }

    private Section readSection(SectionType sectionName, DataInputStream dis) throws IOException {
        Section section = null;
        int sectionSize;
        LOG.info("read Section = " + sectionName);
        switch (sectionName) {
            case PERSONAL:
            case OBJECTIVE:
                section = new TextSection(dis.readUTF());
                LOG.info("Text Section = " + section);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> listItems = null;
                sectionSize = dis.readInt();
                LOG.info("size = " + sectionSize);
                for (int i = 0; i < sectionSize; i++) {
                    listItems.add(dis.readUTF());
                }
                section = new ListSection(listItems);
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Organization> organizationItems = null;
                sectionSize = dis.readInt();
                LOG.info("size = " + sectionSize);
                for (int i = 0; i < sectionSize; i++) {
                    organizationItems.add(readOrganization(dis));
                }
                section = new OrganizationSection(organizationItems);
                break;
        }
        return section;
    }

    private Organization readOrganization(DataInputStream dis) throws IOException {
        List<Organization.Position> positions = null;

        Link link = new Link(dis.readUTF(), dis.readUTF());
        int positionSize = dis.readInt();

        for (int i = 0; i < positionSize; i++) {
            positions.add(readPosition(dis));
        }
        Organization organization = new Organization(link,  positions);
        return organization;
    }

    private Organization.Position readPosition(DataInputStream dis) throws IOException {
        return new Organization.Position(LocalDate.parse(dis.readUTF()),LocalDate.parse(dis.readUTF()),dis.readUTF(),dis.readUTF());
    }
}
