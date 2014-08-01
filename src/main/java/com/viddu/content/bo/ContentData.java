package com.viddu.content.bo;


public class ContentData {
    private final byte[] data;
    private final Integer weight;


    public ContentData(byte[] data, Integer weight) {
        super();
        this.data = data;
        this.weight = weight;
    }

    public byte[] getData() {
        return data;
    }

    public Integer getWeight() {
        return weight;
    }
}
