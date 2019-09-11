package com.chitra.controller;

import java.util.Arrays;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.RegistrationForm;
import com.chitra.domain.Role;
import com.chitra.domain.User;
import com.chitra.repository.RoleRepository;
import com.chitra.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	
	private PasswordEncoder encoder;
	
	public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.encoder = encoder;
	}
	
	@GetMapping
	public String registerForm(Model model) {
		model.addAttribute("register", new RegistrationForm());
		return "registration";
	}
	
	/*
	 * @PostMapping public String processRegistration(RegistrationForm form) {
	 * userRepo.save(form.toUser(encoder)); return "redirect:/login"; }
	 */
	
	@PostMapping
	public String processRegistration(@ModelAttribute("register") @Valid RegistrationForm register, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			log.error("Errors in form");
			return "registration";
		}
		User newUser = new User(register.getUsername(), encoder.encode(register.getPassword()), register.getFullname(), register.getEmail());
		newUser.setId(newUser.hashCode());
		Role role = roleRepo.findByRole("ROLE_USER");
		newUser.setRoles(new HashSet<>(Arrays.asList(role)));
		userRepo.save(newUser);
		return "redirect:/login";
	}
}
