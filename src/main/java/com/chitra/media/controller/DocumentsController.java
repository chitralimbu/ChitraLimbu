package com.chitra.media.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/document")
public class DocumentsController {
	
	@Autowired
	private DocumentService docService;
	
	@GetMapping
	public String uploadPhoto() {
		return "uploadDocument";
	}
	
	@PostMapping("/upload")
	public String addPhoto(@RequestParam("title") String title, @RequestParam("document") MultipartFile document) throws IOException {
		docService.addDocument(title, document);
		log.debug("File uploaded successfully");
		return "redirect:/";
	}
	
}
