package com.example.pcconfighelpercoursework.catalog.filters;

import java.util.List;
import java.util.Map;

public class ExpandableItem {
    private String title;
    private Map<String,Integer> subItems;
    private boolean isExpanded;

    public ExpandableItem(String title, Map<String,Integer> subItems, boolean isExpanded) {
        this.title = title;
        this.subItems = subItems;
        this.isExpanded = isExpanded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, Integer> getSubItems() {
        return subItems;
    }

    public void setSubItems(Map<String, Integer> subItems) {
        this.subItems = subItems;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
