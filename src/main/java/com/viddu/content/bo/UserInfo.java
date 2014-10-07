package com.viddu.content.bo;

public class UserInfo {
    private final String username;
    private final String email;

    public UserInfo(String username, String email) {
        super();
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
