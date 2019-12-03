package com.chitra.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

import com.chitra.prometheus.HomePageHits;
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
import com.chitra.repository.GitRepositoryRepository;
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
	
	@Autowired
	private GitRepositoryRepository gitRepo;

	@Autowired
	private HomePageHits homePageHits;

	private List<String> profile = Arrays.asList("stack", "git", "cv", "linkedin");
	
	
	@GetMapping("/")
	public String home(Model model, HttpServletResponse response) {
		String headerValue = CacheControl.maxAge(7, TimeUnit.DAYS).getHeaderValue();
		model.addAttribute("gitRepo", gitRepo.findAll());
		List<Photo> socialIcon = getAllProfile();
		socialIcon.forEach(social -> model.addAttribute(social.getTitle(), Base64.getEncoder().encodeToString(social.getImage().getData())));
		response.addHeader("Cache-Control", headerValue);
		homePageHits.counterIncrement();
		return "home";
	}
	
	public List<Photo> getAllProfile(){
		return profile.stream()
						.map(photo -> photoService.getPhotoByTitle(photo))
						.collect(Collectors.toList());
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
		experienceRepo.save(toUpdate);
		return "redirect:/profile/"+toUpdate.getOrganisation();
	}
	
	@GetMapping("/profile/delete/{organisation}")
	public String deleteByOrganisation(@PathVariable("organisation") String org) {
		experienceRepo.removeByOrganisation(org);
		return "redirect:/";
	}
	
	@GetMapping("/timeline")
	public String timeline(Model model) {
		List<Experience> allExperience = experienceRepo.findAll();
		Collections.sort(allExperience, new ExperienceComparator());
		model.addAttribute("allExperience", allExperience);
		return "timeline";
	}
}