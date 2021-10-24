package com.urise.webapp.model;

import java.util.List;

public class SkillsSection extends AbstractSection {
    private List<String> items;

    public SkillsSection(List<String> items) {
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "SkillsSection{" +
                "items=" + items +
                '}';
    }
}
