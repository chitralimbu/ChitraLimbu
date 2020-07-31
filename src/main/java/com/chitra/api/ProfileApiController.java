package com.chitra.api;

import java.util.List;
import java.util.stream.Collectors;

import com.chitra.resource.ExperienceResource;
import com.chitra.service.ConvertToResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chitra.domain.Experience;
import com.chitra.repository.ExperienceRepository;

@RestController
@RequestMapping("/api")
public class ProfileApiController {

    @Autowired
    private ExperienceRepository experienceRepo;

    @Autowired
    private ConvertToResourceService convertService;

    @GetMapping(value = "/experience", produces = "application/hal+json")
    public ResponseEntity<List<ExperienceResource>> allExperience() {
        return new ResponseEntity<>(convertService.convertToExperienceResourceList(experienceRepo.findAll()), HttpStatus.OK);
    }

    @GetMapping(value = "/experience/{org}", produces = "application/hal+json")
    public ResponseEntity<ExperienceResource> getExperience(@PathVariable("org") String org) {
    	return new ResponseEntity<>(convertService.convertToExperienceResource(experienceRepo.findByOrganisation(org)), HttpStatus.OK);
    }

    @PostMapping(value = "/experience/new")
    public void createNewExperience(@RequestBody Experience experience) {
    	experienceRepo.save(experience);
    }
}
