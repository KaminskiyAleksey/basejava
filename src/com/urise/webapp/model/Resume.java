package com.urise.webapp.model;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contact;
    private Map<SectionType, AbstractSection> section;

    public Resume(String fullName) {
        this.uuid = UUID.randomUUID().toString();
        new Resume(this.uuid, fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContact() {
        return contact;
    }

    public void setContact(Map<ContactType, String> contact) {
        this.contact = contact;
    }

    public Map<SectionType, AbstractSection> getSection() {
        return section;
    }

    public void setSection(Map<SectionType, AbstractSection> section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName) && contact.equals(resume.contact) && section.equals(resume.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contact, section);
    }

    @Override
    public String toString() {
        return uuid + '(' + fullName + ')';
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}
