package com.chitra.media.controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Photo;
import com.chitra.service.PhotoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/media")
public class PhotoController {

	@Autowired
	PhotoService photoService;
	
	@GetMapping
	public String media() {
		return "hello world";
	}
	
	@PostMapping("/photos/add")
	public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, Model model) throws IOException{
		log.debug("Inside addPhoto");
		String id = photoService.addPhoto(title, image);
		return "redirect:/media/photos/"+id;
	}

	@GetMapping("/photos/upload")
	public String uploadPhoto() {
		return "uploadPhoto";
	}

	@GetMapping("/photos/{id}")
	public String getPhoto(@PathVariable String id, Model model) {
		Photo photo = photoService.getPhoto(id);
		model.addAttribute("title", photo.getTitle());
		model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
		return "photos";
	}
	
	@GetMapping("/photos/title/{title}")
	public String getTitlePhoto(@PathVariable String title, Model model) {
		Photo photo = photoService.getPhotoByTitle(title);
		model.addAttribute("title", photo.getTitle());
		model.addAttribute("image", Base64.getEncoder().encodeToString(photo.getImage().getData()));
		return "photos";
	}
	
}
