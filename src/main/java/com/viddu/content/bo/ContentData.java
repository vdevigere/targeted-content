package com.viddu.content.bo;

public class ContentData {
    private String data;

    private Integer weight;

    public ContentData(String data, Integer weight) {
        this.data = data;
        this.weight = weight;
    }

    public ContentData() {
        // TODO Auto-generated constructor stub
    }

    public String getData() {
        return data;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
