package com.viddu.content.bo;


public class Status {

    private String message;
    private Type type;

    public Status(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public enum Type {
        SUCCESS("alert-success"), INFO("alert-info"), WARNING("alert-warning"), DANGER("alert-danger");
        private final String style;

        Type(String style) {
            this.style = style;
        }

        public String getStyle() {
            return style;
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
