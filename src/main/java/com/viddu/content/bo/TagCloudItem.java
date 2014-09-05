package com.viddu.content.bo;

public class TagCloudItem {
    private final String name;
    private final Long size;

    public TagCloudItem(String name, Long size) {
        super();
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Long getSize() {
        return size;
    }


}
