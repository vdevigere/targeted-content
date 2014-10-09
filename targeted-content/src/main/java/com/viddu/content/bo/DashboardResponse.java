package com.viddu.content.bo;


public class DashboardResponse<T> {

    private final String message;
    private final Type type;
    private final T response;

    public DashboardResponse(Type type, String message, T response) {
        this.type = type;
        this.message = message;
        this.response = response;
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

    public Type getType() {
        return type;
    }

    public T getResponse() {
        return response;
    }
}
