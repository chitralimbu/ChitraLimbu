package com.chitra.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.Experience;
import com.chitra.domain.Role;
import com.chitra.domain.Tasks;
import com.chitra.domain.User;
import com.chitra.repository.ExperienceRepository;
import com.chitra.repository.RoleRepository;
import com.chitra.repository.TasksRepository;
import com.chitra.repository.UserRepository;

@Controller
@RequestMapping("/private/setup/all")
public class SetupController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ExperienceRepository expRepo;

	@Autowired
	private TasksRepository taskRepo;
	
	@Autowired
	private PasswordEncoder encoder;

	@GetMapping
	public String setup() {
		roleSetup();
		userSetup();
		expSetup();
		return "redirect:/login";
	}


	private void roleSetup() {
		Role user = new Role();
		user.setRole("ROLE_USER");
		Role admin = new Role();
		admin.setRole("ROLE_ADMIN");
		roleRepo.save(user);
		roleRepo.save(admin);
	}

	private void userSetup() {
		User admin = new User();
		Set<Role> thisRole = new HashSet<>();
		thisRole.add(roleRepo.findByRole("ROLE_ADMIN"));
		admin.setEmail("admin@admin.com");
		admin.setUsername("administrator");
		admin.setPassword(encoder.encode("Cptgbdllmrh150!"));
		admin.setRoles(thisRole);
		userRepo.save(admin);
	}

	private void expSetup() {
		Tasks task1 = new Tasks(); task1.setTask("Monitor");
		taskRepo.save(task1);
		Tasks task2 = new Tasks(); task2.setTask("Automate");
		taskRepo.save(task2);
		Experience tata = new Experience();
		tata.setTitle("Tata Consultancy Services"); tata.setStartDate("March 2018");
		tata.setEndDate("Present");
		tata.setOrganisation("Tata Consultancy Services");
		tata.setDescription("Java Developer"); tata.setTasks(Arrays.asList(task1,
		task2)); 
		expRepo.save(tata);
	}

}