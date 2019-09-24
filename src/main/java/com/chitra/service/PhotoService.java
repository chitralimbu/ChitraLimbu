package com.chitra.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Photo;
import com.chitra.repository.PhotoRepository;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository photoRepo;
	
	public String addPhoto(String title, MultipartFile file) throws IOException{
		Photo photo = new Photo();
		photo.setTitle(title);
		photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		photo = photoRepo.insert(photo);
		return photo.getId();
	}
	
	public Photo getPhoto(String id) {
		return photoRepo.findById(id).get();
	}
	//@Cacheable("photos")
	public Photo getPhotoByTitle(String title) {
		return photoRepo.findByTitle(title);
	}
}
