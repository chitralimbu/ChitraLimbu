package com.chitra.media.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chitra.media.domain.Video;
import com.chitra.service.VideoService;

@Controller
@RequestMapping("/media/videos")
public class VideoController {

	@Autowired
	VideoService videoService;

	@GetMapping("/upload")
	public String uploadVideo() {
		return "uploadVideo";
	}

	@PostMapping("/add")
	public String addVideo(@RequestParam("title") String title, 
			@RequestParam("file") MultipartFile file, Model model) throws IOException {
		String id = videoService.addVideo(title, file);
		return "redirect:/media/videos/" + id;
	}

	@GetMapping("/{id}")
	public String getVideo(@PathVariable String id, Model model) throws Exception {
	    Video video = videoService.getVideo(id);
	    model.addAttribute("title", video.getTitle());
	    model.addAttribute("url", "stream/" + id);
	    return "videos";
	}

	@GetMapping("/stream/{id}")
	public void streamVideo(@PathVariable String id, HttpServletResponse response) throws Exception {
	    Video video = videoService.getVideo(id);
	    FileCopyUtils.copy(video.getStream(), response.getOutputStream());        
	}
}
