package com.chitra.service;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Video;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;

@Slf4j
@Service
public class VideoService {

	@Autowired
	private GridFsTemplate gridFsTemplate;

	@Autowired
	private GridFsOperations operations;

	public String addVideo(String title, MultipartFile file) throws IOException { 
		DBObject metaData = new BasicDBObject(); 
		metaData.put("type", "video"); 
		metaData.put("title", title); 
		ObjectId id = gridFsTemplate.store(
				file.getInputStream(), file.getName(), file.getContentType(), metaData); 
		log.info(String.format("Successfully uploaded video with title %s", title));
		return id.toString();
	}

	public Video getVideo(String id) throws IllegalStateException, IOException { 
		GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))); 
		Video video = new Video(); 
		video.setTitle(file.getMetadata().get("title").toString()); 
		video.setStream(operations.getResource(file).getInputStream());
		return video; 
	}
}
