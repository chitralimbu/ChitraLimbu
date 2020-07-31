package com.chitra.media.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	public String addPhoto(@RequestParam("title") String title, @RequestParam("image") MultipartFile image, @RequestParam("update") Optional<String> update, Model model) throws IOException{
		//String id = photoService.addPhoto(title, image);
		if(update.isPresent()){
			log.info(String.format("Updating photo with title %s", title));
			Photo toUpdate = photoService.getPhotoByTitle(title);
			toUpdate.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
			photoService.updatePhoto(toUpdate);
		}else{
			log.info(String.format("Uploading new photo with title %s", title));
			photoService.addPhoto(title, image);
		}
		return "redirect:/media/photos/all";
		/*return "redirect:/media/photos/"+id;*/
	}

	@GetMapping("/photos/upload")
	public String uploadPhoto(@RequestParam("update") Optional<String> title, Model model) {
		if(title.isPresent()){
			model.addAttribute("update", title.get());
		}
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

	@GetMapping("/photos/all")
	public String getAllPhoto(Model model){
		List<Photo> allPhotos = photoService.getAllPhoto();
		Map<String, String> allPhotoString = allPhotos.stream().collect(Collectors.toMap(photo -> photo.getTitle(), photo -> convertPhotoToString(photo)));
		model.addAttribute("allPhotos", allPhotoString);
		return "photo";
	}

	@PostMapping("/photos/delete/{title}")
	public String deletePhoto(@PathVariable String title){
		photoService.deletePhotoByTitle(title);
		return "redirect:/media/photos/all";
	}

	private String convertPhotoToString(Photo photo){
		return Base64.getEncoder().encodeToString(photo.getImage().getData());
	}
}
