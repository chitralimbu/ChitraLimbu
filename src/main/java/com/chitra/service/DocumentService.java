package com.chitra.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Documents;
import com.chitra.repository.DocumentRepository;

@Service
public class DocumentService {
	
	@Autowired
	private DocumentRepository docRepo;
	
	public void addDocument(String title, MultipartFile file) throws IOException{
		Documents doc = new Documents();
		doc.setTitle(title);
		doc.setDoc(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
		docRepo.insert(doc);
	}
	
	public Documents getDocByTitle(String title) {
		return docRepo.findByTitle(title);
	}
	
	public Documents getDocById(String id) {
		return docRepo.findById(id).get();
	}
	
}
