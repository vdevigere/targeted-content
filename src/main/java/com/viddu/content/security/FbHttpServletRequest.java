package com.viddu.content.security;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.viddu.content.bo.UserInfo;

public class FbHttpServletRequest extends HttpServletRequestWrapper {

    private final UserInfo user;
    private Principal principal;

    public FbHttpServletRequest(HttpServletRequest request, UserInfo user) {
        super(request);
        this.user = user;
        this.principal = new Principal() {

            @Override
            public String getName() {
                return user.getFirst_name() + ", " + user.getLast_name();
            }
        };

    }

    @Override
    public boolean isUserInRole(String role) {
        return (role.equalsIgnoreCase(user.getRole()));
    }

    @Override
    public String getAuthType() {
        return super.getAuthType();
    }

    @Override
    public Principal getUserPrincipal() {
        return this.principal;
    }

    @Override
    public boolean isSecure() {
        return super.isSecure();
    }

}
