package com.chitra.service;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Photo;
import com.chitra.repository.PhotoRepository;

@Slf4j
@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepo;
	
	public String addPhoto(String title, MultipartFile file) throws IOException{
		Photo photo = new Photo();
		photo.setTitle(title);
		photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		photo = photoRepo.insert(photo);
		log.info(String.format("Successfully uploaded photo with title %s", title));
		return photo.getId();
	}

	public void updatePhoto(Photo photo){
		photoRepo.save(photo);
		log.info(String.format("Successfully updated photo with title %s", photo.getTitle()));
	}
	
	public Photo getPhoto(String id) {
		return photoRepo.findById(id).get();
	}
	//@Cacheable("photos")
	public Photo getPhotoByTitle(String title) {
		return photoRepo.findByTitle(title);
	}

	public List<Photo> getAllPhoto(){
		return photoRepo.findAll();
	}

	public void deletePhotoByTitle(String title){
		photoRepo.deleteByTitle(title);
	}
}
