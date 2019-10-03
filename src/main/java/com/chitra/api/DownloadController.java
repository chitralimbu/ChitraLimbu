package com.chitra.api;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chitra.media.domain.Documents;
import com.chitra.repository.DocumentRepository;

@RestController
@RequestMapping("/download")
public class DownloadController {
	
	@Autowired
	private DocumentRepository docRepo;
	
	@GetMapping("/document/{title}")
	public ResponseEntity<Resource> download(@PathVariable("title") String title){
		HttpHeaders header = new HttpHeaders();
		Documents doc = docRepo.findByTitle(title);
		header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", doc.getTitle()));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        
        ByteArrayResource resource = new ByteArrayResource(doc.getDoc().getData());
        return ResponseEntity.ok().headers(header)
        		.contentLength(resource.contentLength())
        		.body(resource);
	}
	
}
