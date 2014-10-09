package com.viddu.api.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.viddu.api.bo.UserInfo;

public class FacebookOAuthFilter implements Filter {

    private static final String USER_INFO = "USER_INFO";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final Token EMPTY_TOKEN = null;
    Config config = ConfigFactory.load();

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(FacebookOAuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest webRequest = (HttpServletRequest) request;
        HttpServletResponse webResponse = (HttpServletResponse) response;

        HttpSession session = webRequest.getSession(true);
        if (session.getAttribute(ACCESS_TOKEN) == null) {
            String code = webRequest.getParameter("code");
            String apiKey = config.getString("facebook-app-id");
            String apiSecret = config.getString("facebook-app-secret");
            OAuthService service = new ServiceBuilder().provider(FacebookApi.class).apiKey(apiKey).apiSecret(apiSecret)
                    .callback(buildFullURL(webRequest)).build();
            if (code != null && !code.isEmpty()) {
                Verifier verifier = new Verifier(code);
                Token accessToken = service.getAccessToken(EMPTY_TOKEN, verifier);
                session.setAttribute(ACCESS_TOKEN, accessToken);
                // Fetch User Information and add to SecurityContext
                OAuthRequest fbRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
                service.signRequest(accessToken, fbRequest);
                Response fbResponse = fbRequest.send();
                if (fbResponse.isSuccessful()) {
                    String userDetails = fbResponse.getBody();
                    logger.debug("User Details={}", userDetails);
                    UserInfo userInfo = mapper.readValue(userDetails, UserInfo.class);
                    userInfo.setRole("LOGGED_IN");
                    session.setAttribute(USER_INFO, userInfo);
                    HttpServletRequest wrappedReq = new FbHttpServletRequest(webRequest, userInfo);
                    // Continue Chain
                    chain.doFilter(wrappedReq, response);
                }else{
                    logger.error("Error Fetching User Information");
                    webResponse.sendError(403, "Unable to Authorize");
                }
            } else {
                // Initiate Facebook Auth workflow.
                String fbAuthUrl = service.getAuthorizationUrl(EMPTY_TOKEN);
                webResponse.sendRedirect(fbAuthUrl);
            }
        }else{
            UserInfo userInfo = (UserInfo) session.getAttribute(USER_INFO);
            HttpServletRequest wrappedReq = new FbHttpServletRequest(webRequest, userInfo);
            // Continue Chain
            chain.doFilter(wrappedReq, response);
        }

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    protected static String buildFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
