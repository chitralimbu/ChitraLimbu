package com.chitra.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chitra.domain.Experience;
import com.chitra.media.domain.Photo;
import com.chitra.repository.ExperienceRepository;
import com.chitra.service.ExperienceComparator;
import com.chitra.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	private ExperienceRepository experienceRepo;
	
	@Autowired 
	private PhotoService photoService;
	
	@GetMapping("/")
	public String home(Model model, HttpServletResponse response) {
		String headerValue = CacheControl.maxAge(1, TimeUnit.DAYS).getHeaderValue();
		List<Experience> allExperience = experienceRepo.findAll();
		Collections.sort(allExperience, new ExperienceComparator());
		log.debug(String.format("allExperiencesize %d", allExperience.size()));
		model.addAttribute("allExperience", allExperience);
		Photo photo = photoService.getPhotoByTitle("mainBackground");
		Photo profile = photoService.getPhotoByTitle("profile");
		model.addAttribute("mainBackground", Base64.getEncoder().encodeToString(photo.getImage().getData()));
		model.addAttribute("profile", Base64.getEncoder().encodeToString(profile.getImage().getData()));
		response.addHeader("Cache-Control", headerValue);
		return "home";
	}
	
	@GetMapping("/profile/new")
	public String newProfile(Model model) {
		model.addAttribute("experience", new Experience());
		model.addAttribute("task", "");
		return "profile";
	}
	
	@GetMapping("/profile/{title}")
	public String editProfile(@PathVariable("title") String title, Model model) {
		Experience exp = experienceRepo.findByOrganisation(title);
		String allTasks = exp.getTasks().stream().collect(Collectors.joining("\n"));
		model.addAttribute("experience", exp);
		model.addAttribute("task", allTasks);
		return "profile";
	}
	
	@PostMapping("/profile/update")
	public String updateProfile(@ModelAttribute Experience exp, @RequestParam String task) {
		Experience toUpdate = Optional.ofNullable(experienceRepo.findByTitle(exp.getTitle())).orElse(exp);
		toUpdate.setTitle(exp.getTitle());
		toUpdate.setStartDate(exp.getStartDate());
		toUpdate.setEndDate(exp.getEndDate());
		toUpdate.setDescription(exp.getDescription());
		toUpdate.setTasks(Arrays.asList(task.split("\n")));
		//exp.setTasks(Arrays.asList(task.split("\n")));		
		experienceRepo.save(toUpdate);
		return "redirect:/profile/"+toUpdate.getOrganisation();
	}
}
