package com.chitra.resource;

import com.chitra.api.ProfileApiController;
import com.chitra.domain.Experience;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class ExperienceResource extends ResourceSupport {

    private final Experience experience;

    public ExperienceResource(final Experience experience) {
        this.experience = experience;
        final String experienceOrg = experience.getOrganisation();
        add(linkTo(methodOn(ProfileApiController.class).allExperience()).withRel("all"));
        add(linkTo(methodOn(ProfileApiController.class).getExperience(experienceOrg)).withSelfRel());
    }
}
