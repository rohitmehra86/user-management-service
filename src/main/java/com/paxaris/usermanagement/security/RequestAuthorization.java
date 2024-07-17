package com.paxaris.usermanagement.security;

import java.lang.annotation.*;

/**
 * @author Rohit Mehra
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestAuthorization {
}
