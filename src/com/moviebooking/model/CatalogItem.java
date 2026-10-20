package com.moviebooking.model;

/** Shared catalog behavior used by catalog entries. */
public abstract class CatalogItem {
    private final String id;
    private final String title;

    protected CatalogItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public abstract String getDisplayLabel();
}
