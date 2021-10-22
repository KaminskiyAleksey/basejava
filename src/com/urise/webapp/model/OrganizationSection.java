package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection{
    private List<Organization> items ;

    public OrganizationSection(List<Organization> items) {
        this.items = items;
    }

    public List<Organization> getItems() {
        return items;
    }


}
