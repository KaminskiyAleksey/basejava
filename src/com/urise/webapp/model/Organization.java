package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String organizationName;
    private List<Position> position = new ArrayList<>();

    public Organization(String organizationName, List<Position> position) {
        this.organizationName = organizationName;
        this.position = position;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public List<Position> getPosition() {
        return position;
    }
}
