package com.viddu.api.bo;

public class TagCloudItem {
    private final String name;
    private final Long weight;

    public TagCloudItem(String name, Long weight) {
        super();
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Long getWeight() {
        return weight;
    }

}
