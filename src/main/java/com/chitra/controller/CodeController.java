package com.chitra.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpRequest;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.service.CodeService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequestMapping("/code")
public class CodeController {
	
	@Autowired CodeService codeService;
	
	@Autowired
	private GitRepositoryRepository gitRepo;

	@GetMapping
	public String getCodeParam(@RequestParam("page") Optional<Integer> page, Model model) {
		int number = page.isPresent() ? page.get() : 1;
		PageRequest pageable = PageRequest.of(number -1, 10);
		Page<GitRepository> pageRepository = gitRepo.findAll(pageable);
		int totalPages = pageRepository.getTotalPages();
		if(totalPages > 0){
			List<Integer> pageNumbers = IntStream.rangeClosed(1,totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("activeGitList", true);
		model.addAttribute("gitList", pageRepository.getContent());
		return "code-pageable";
	}


	/*@GetMapping
	public String getAllCode(Model model) {
		model.addAttribute("gitRepo", gitRepo.findAll());
		return "code";
	}*/

	@GetMapping("/{name}")
	public String topicCode(Model model, @PathVariable("name") String name) {
		GitRepository repository = gitRepo.findByName(name);
		List<GitRepositoryContents> repoContents = codeService.turncatePath(repository.getAllContents());
		List<String> allHeadings = codeService.generateAllHeadings(repoContents).stream().distinct().collect(Collectors.toList());
		model.addAttribute("headings", allHeadings);
		model.addAttribute("repository", repository);
		model.addAttribute("repositoryContents", repoContents);
		return "code-topic";
	}

	@GetMapping("/search")
	public String searchResults(@RequestParam("search") String search, RestTemplate restTemplate, @RequestParam("page") Optional<Integer> page, Model model, HttpServletRequest request){
		/*String allSearchResult = restTemplate.getForObject("")
		model.addAttribute("pageNumbers", 1);
		model.addAttribute("activeGitList", true);
		model.addAttribute("gitList", pageRepository.getContent());*/

		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		log.info(scheme + serverName + serverPort + contextPath);

		return "redirect:/";
	}
}
