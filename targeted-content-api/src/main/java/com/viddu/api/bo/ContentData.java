package com.viddu.api.bo;

public class ContentData<T> {
    private T data;

    private Integer weight;

    public ContentData(T data, Integer weight) {
        this.data = data;
        this.weight = weight;
    }

    public ContentData() {
        // TODO Auto-generated constructor stub
    }

    public T getData() {
        return data;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
