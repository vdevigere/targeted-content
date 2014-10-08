package com.viddu.content.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private final String id;
    private final String email;
    private final String first_name;
    private final String gender;
    private final String last_name;
    private final String link;
    private String role;

    public UserInfo(@JsonProperty("id") String id, @JsonProperty("email") String email,
            @JsonProperty("first_name") String first_name, @JsonProperty("gender") String gender,
            @JsonProperty("last_name") String last_name, @JsonProperty("link") String link) {
        super();
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.gender = gender;
        this.last_name = last_name;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getGender() {
        return gender;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLink() {
        return link;
    }

    public String getRole() {
        // TODO Auto-generated method stub
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
