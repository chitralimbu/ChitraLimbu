package com.chitra.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chitra.domain.Experience;
import com.chitra.media.domain.Photo;
import com.chitra.repository.ExperienceRepository;
import com.chitra.repository.TasksRepository;
import com.chitra.service.PhotoService;

@Controller
public class HomeController {
	
	@Autowired
	private ExperienceRepository experienceRepo;
	
	@Autowired 
	private PhotoService photoService;
	
	@GetMapping("/")
	public String home(Model model) {
		List<Experience> allExperience = experienceRepo.findAll();
		model.addAttribute("allExperience", allExperience);
		Photo photo = photoService.getPhotoByTitle("mainBackground");
		Photo profile = photoService.getPhotoByTitle("profile");
		model.addAttribute("mainBackground", Base64.getEncoder().encodeToString(photo.getImage().getData()));
		model.addAttribute("profile", Base64.getEncoder().encodeToString(profile.getImage().getData()));
		return "home";
	}
	
	@GetMapping("/profile/new")
	public String newProfile() {
		return "newProfile";
	}
	
	@GetMapping("/profile/{title}")
	public String editProfile(@PathVariable("title") String title, Model model) {
		Experience exp = experienceRepo.findByTitle(title);
		model.addAttribute("experience", exp);
		return "profile";
	}
	/*
	 * @GetMapping("/home/add-experience") public String addExperience() { Tasks
	 * task1 = new Tasks(); task1.setTask("Monitor");
	 * 
	 * tasksRepo.save(task1);
	 * 
	 * Tasks task2 = new Tasks(); task2.setTask("Automate");
	 * 
	 * tasksRepo.save(task2);
	 * 
	 * Experience tata = new Experience();
	 * tata.setTitle("Tata Consultancy Services"); tata.setStartDate("March 2018");
	 * tata.setEndDate("Present");
	 * tata.setOrganisation("Tata Consultancy Services");
	 * tata.setDescription("Java Developer"); tata.setTasks(Arrays.asList(task1,
	 * task2)); experienceRepo.save(tata);
	 * 
	 * return "redirect:/home"; }
	 */
}
