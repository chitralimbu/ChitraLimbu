package com.chitra.controller;

import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.repository.GitRepositoryRepository;
import com.chitra.service.CodeService;
import com.chitra.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/code")
public class CodeController {
	
	@Autowired
	private CodeService codeService;
	
	@Autowired
	private GitRepositoryRepository gitRepo;

	@Autowired
	private SearchService searchService;

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
	public String searchResults(@RequestParam("search") String search, Model model){
		log.info("Checking db for results for search term: " + search);
		long now = System.currentTimeMillis();
		List<GitRepository> allMatch = gitRepo.findByNameContainingIgnoreCase(search);
		long end = System.currentTimeMillis() - now;
		log.info(String.format("Searching %s took %d milliseconds", search, end));
		if(allMatch.isEmpty()){
			allMatch.add(new GitRepository("-1", "", "no_name", "no_url", String.format("No results found for: %s",search)));
		}
		model.addAttribute("pageNumbers", 1);
		model.addAttribute("activeGitList", true);
		model.addAttribute("gitList", allMatch);
		return "code-pageable";
	}
}
