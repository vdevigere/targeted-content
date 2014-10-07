package com.viddu.content.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.viddu.content.bo.UserInfo;

@SecuredBy
public class SecuredByFilter implements ContainerRequestFilter {

    @Context
    HttpServletRequest webRequest;

    @Inject
    Config config;

    private static final Logger logger = LoggerFactory.getLogger(SecuredByFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        final HttpSession session = webRequest.getSession();
        String username = (String) session.getAttribute("USERNAME");
        UriInfo uriInfo = requestContext.getUriInfo();
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();

        // User is already logged in and we have details of the user.
        if (username != null && !username.isEmpty()) {
            // User already authenticated verify auth.
            if (isAuthorized(username))
                return;
            else
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        } else {
            // User signed in, now fetch user Information
            if (queryParams.containsKey("code")) {
                // Validate Token and Code with FB
                String code = queryParams.getFirst("code");
                String redirect_url = queryParams.getFirst("state");
                UserInfo userInfo = fetchUserInfo(code, redirect_url);
                if (userInfo != null) {
                    session.setAttribute("USERNAME", userInfo.getUsername());
                } else {
                    requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                }
            } else {
                // User has not signed in yet, take user to FB.
                try {
                    String appId = config.getString("facebook-app-id");
                    String currentURI = buildFullURL(webRequest);
                    Map<String, String> fbParams = new HashMap<>();
                    fbParams.put("display", "popup");
                    fbParams.put("client_id", appId);
                    fbParams.put("state", currentURI);
                    fbParams.put("redirect_uri", currentURI);
                    String fbUrl = buildFBUrl(fbParams);
                    URI loginPage = new URI(fbUrl);
                    // Redirect to login
                    requestContext.abortWith(Response.temporaryRedirect(loginPage).build());
                } catch (URISyntaxException e) {
                    logger.debug("Bad URI Syntax", e);
                    requestContext.abortWith(Response.serverError().build());
                }
            }
        }
    }

    private String buildFBUrl(Map<String, String> fbParams) {
        StringBuilder fbURL = new StringBuilder("https://www.facebook.com/dialog/oauth");

        if (fbParams == null || fbParams.isEmpty()) {
            return fbURL.toString();
        } else {
            fbURL.append("?");
            fbParams.forEach((key, value) -> {
                fbURL.append(key).append("=").append(value).append("&");
            });
            return fbURL.toString();
        }
    }

    private UserInfo fetchUserInfo(String code, String currentURI) {
        // TODO Make a call to Facebook API and get user details
        return new UserInfo("vdevigere", "vdevigere@yahoo.com");
    }

    private boolean isAuthorized(String username) {
        // TODO Add logic to check for authorization.
        return true;
    }

    public static String buildFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
