package com.viddu.content.tiles;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;
import org.apache.tiles.request.ApplicationContext;
import org.apache.tiles.request.Request;
import org.apache.tiles.request.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Produces("text/html")
public class TilesMessageBodyWriter implements MessageBodyWriter<ModelView> {

    @Context
    private ServletContext context;

    @Context
    private HttpServletRequest request;

    @Context
    private HttpServletResponse response;

    private Logger logger = LoggerFactory.getLogger(TilesMessageBodyWriter.class);

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == ModelView.class;
    }

    @Override
    public long getSize(ModelView t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeTo(ModelView modelView, Class<?> type, Type genericType, Annotation[] annotations,
            MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        final ApplicationContext applicationContext = org.apache.tiles.request.servlet.ServletUtil
                .getApplicationContext(context);

        final TilesContainer container = TilesAccess.getContainer(applicationContext);
        HttpServletResponse wrappedResponse = new HttpServletResponseWrapper(response);
        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request);
        Map<String, ?> model = modelView.getModel();
        if (model != null) {
            for (String key : model.keySet()) {
                wrappedRequest.setAttribute(key, model.get(key));
            }
        }
        Request tilesRequestResponse = new ServletRequest(applicationContext, wrappedRequest, wrappedResponse);
        container.render(modelView.getView(), tilesRequestResponse);
        logger.debug("{}", modelView.getView());
    }
}
