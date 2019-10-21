package com.chitra.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/blog/upload")
public class FileUploadController {
	
	
	@GetMapping
	public String listUploadedFiles(Model model) throws IOException{
		return "uploadForm";
	}
	
	@PostMapping
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		redirectAttributes.addFlashAttribute("message", "you successfully uploaded" + file.getOriginalFilename() + "!");
		
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		System.out.println("ROOT DIRECTORY: " + rootDirectory);
		
		if(file != null && !file.isEmpty()) {
			try {
				file.transferTo(new File("C:\\Users\\limbu\\Documents\\workspace-sts-3.9.7.RELEASE\\ChitraLimbu\\src\\main\\resources\\static\\images\\"+"TEST.jpg"));
			}catch(Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
			}
		}
		
		return "uploadForm";
	}
	
	
}
