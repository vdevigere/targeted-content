package com.viddu.content.resource;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.viddu.content.bo.Content;
import com.viddu.content.bo.ContentTag;
import com.viddu.content.redis.ContentDAO;

@Path("/content")
@ApplicationScoped
public class ContentResource {

	@Inject
	ContentDAO contentDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(ContentResource.class);

	@GET
	@Path("/{id}")
	@Produces("application/json")
	public Content getById(@PathParam("id") Long id,
			@DefaultValue("1") @QueryParam("depth") Integer depth) {
		return contentDAO.getContentById(id, depth);
	}

	@GET
	@Path("/{id}/tags")
	@Produces("application/json")
	public Set<ContentTag> getAllTags(@PathParam("id") Long id,
			@DefaultValue("1") @QueryParam("depth") Integer depth) {
		return contentDAO.getTagsByContentId(id, depth);
	}

	@POST
	@Path("/post")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("application/json")
	public String saveContent(@BeanParam Content content,
			@FormParam("tags") String tags) {
		logger.debug("Name={}", content.getName());
		logger.debug("URL={}", content.getUrl());
		logger.debug("Type={}", content.getType());
		logger.debug("Tags={}", tags);
		Long id = null;
		return "Success";
	}
}
