package com.viddu.content.bo;


public class ContentData {
    private final byte[] data;
    private final Integer weight;

    public ContentData() {
        this("", 0);
    }

    public ContentData(String data, Integer weight) {
        super();
        this.data = data.getBytes();
        this.weight = weight;
    }

    public byte[] getData() {
        return data;
    }

    public Integer getWeight() {
        return weight;
    }
}
