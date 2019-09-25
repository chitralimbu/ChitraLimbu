package com.chitra.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	
	@GetMapping(value="/experience", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Experience> allExperience() {
		return experienceRepo.findAll();
	}
	
	@GetMapping(value="/experience/{org}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Experience getExperience(@PathVariable("org") String org) {
		return experienceRepo.findByOrganisation(org);
	}
	
	@PostMapping(value="/experience/new")
	@ResponseStatus(HttpStatus.CREATED)
	public void createNewExperience(@RequestBody Experience experience) {	
		experienceRepo.save(experience);
	}
}
