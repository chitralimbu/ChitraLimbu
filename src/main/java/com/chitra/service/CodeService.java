package com.chitra.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.chitra.domain.GitRepository;
import com.chitra.domain.GitRepositoryContents;
import com.chitra.domain.GitRepositoryRecursive;

@Service
public class CodeService {

	public List<String> generateAllHeadings(List<GitRepositoryContents> repoContents){
		List<String> allHeadings = new ArrayList<>();
		for(GitRepositoryContents contents: repoContents) {
			if(contents.getType().equals("dir")) {
				for(GitRepositoryRecursive allRec: contents.getRecursive()) {
					String fullPath = String.format("%s",allRec.getPath());
					allHeadings.add(filterPath(fullPath));
				}
			}else {
				allHeadings.add(contents.getPath());
			}
			
		}
		return allHeadings;
	}
	
	public String filterPath(String fullPath) {
		List<String> fullPathSplit = Arrays.asList(fullPath.split("/"));
		return fullPathSplit.get(fullPathSplit.size() -1);
	}
	
	public List<GitRepositoryContents> turncatePath(List<GitRepositoryContents> gitRepositoryContents){
		for(GitRepositoryContents gitRepoContents: gitRepositoryContents) {
			List<GitRepositoryRecursive> listRecur = gitRepoContents.getRecursive();
			if(listRecur != null) {
				for(GitRepositoryRecursive recur : gitRepoContents.getRecursive()) {
					recur.setPath(filterPath(recur.getPath()));
				}
			}
		}
		return gitRepositoryContents;
	}
	
	public List<String> findURLs(List<GitRepository> allRepositories){
		List<String> allURL = new ArrayList<>();
		
		return allURL;
	}
}
