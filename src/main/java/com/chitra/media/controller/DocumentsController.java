package com.chitra.media.controller;

import java.io.IOException;
import java.util.Optional;

import com.chitra.media.domain.Documents;
import com.chitra.media.domain.Photo;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.service.DocumentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/media/document")
public class DocumentsController {
	
	@Autowired
	private DocumentService docService;
	
	@GetMapping("/upload")
	public String uploadDocument(@RequestParam("update") Optional<String> title, Model model) {
		if(title.isPresent()){
			model.addAttribute("update", title.get());
		}
		return "uploadDocument";
	}
	
	@GetMapping("/all")
	public String showAllDocuments(Model model){
		model.addAttribute("allDocs", docService.getAllDocuments());
		return "documents";
	}

	@PostMapping("/add")
	public String addPhoto(@RequestParam("title") String title, @RequestParam("document") MultipartFile document, @RequestParam("update") Optional<String> update, Model model) throws IOException {
		if(update.isPresent()){
			log.info(String.format("Updating document with title %s", title));
			Documents doc = docService.getDocByTitle(title);
			doc.setDoc(new Binary(BsonBinarySubType.BINARY, document.getBytes()));
			docService.updateDocument(doc);
		}else{
			log.info(String.format("Uploading new document with title %s", title));
			docService.addDocument(title, document);
		}
		log.debug("File uploaded successfully");
		return "redirect:/media/document/all";
	}

	@PostMapping("/delete/{title}")
	public String deletePhoto(@PathVariable String title){
		docService.deleteDocByTitle(title);
		return "redirect:/media/document/all";
	}

}
