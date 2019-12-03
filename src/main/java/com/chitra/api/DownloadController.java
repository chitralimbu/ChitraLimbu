package com.chitra.api;

import com.chitra.prometheus.CVDownloadHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

	@Autowired
	private CVDownloadHits cvDownloadHits;

	@GetMapping("/document/{title}")
	public ResponseEntity<Resource> download(@PathVariable("title") String title){
		HttpHeaders header = new HttpHeaders();
		Documents doc = docRepo.findByTitle(title);
		doc.setCount(doc.getCount()+1);
		docRepo.save(doc);
		header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", doc.getTitle()));
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        cvDownloadHits.counterIncrement();
        ByteArrayResource resource = new ByteArrayResource(doc.getDoc().getData());
        return ResponseEntity.ok().headers(header)
        		.contentLength(resource.contentLength())
        		.contentType(MediaType.APPLICATION_PDF)
        		.body(resource);
	}
	
}
