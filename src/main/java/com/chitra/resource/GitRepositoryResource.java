package com.chitra.resource;

import com.chitra.api.GitApiController;
import com.chitra.controller.CodeController;
import com.chitra.domain.GitRepository;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.ui.Model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class GitRepositoryResource extends ResourceSupport {

    private final GitRepository gitRepository;

    public GitRepositoryResource(final GitRepository gitRepository){
        this.gitRepository = gitRepository;
        final String name = gitRepository.getName();
        add(linkTo(CodeController.class).slash(name).withRel("url"));
        add(linkTo(GitApiController.class).slash(name).withRel("local-git-api"));
    }
}