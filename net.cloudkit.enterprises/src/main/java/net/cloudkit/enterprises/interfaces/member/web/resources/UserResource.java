package net.cloudkit.enterprises.interfaces.member.web.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class UserResource extends Resource<UserResource> {

    public UserResource(UserResource content, Link... links) {
        super(content, links);
    }

    public UserResource(UserResource content, Iterable<Link> links) {
        super(content, links);
    }
}
