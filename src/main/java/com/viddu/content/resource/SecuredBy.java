package com.viddu.content.resource;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.ws.rs.NameBinding;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface SecuredBy {
    String role() default "Any";
}
