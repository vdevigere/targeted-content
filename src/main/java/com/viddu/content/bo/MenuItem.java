package com.viddu.content.bo;

public class MenuItem {

    private final String link;
    private final String name;
    private final String active;

    public MenuItem(String name, String link, boolean isActive) {
        this.name = name;
        this.link = link;
        this.active = (isActive) ? "active" : "";
    }

    public MenuItem(String name, String link) {
        this(name, link, false);
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getActive() {
        return active;
    }

}
