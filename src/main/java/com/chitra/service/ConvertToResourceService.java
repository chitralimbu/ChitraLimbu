package com.chitra.service;

import com.chitra.domain.Experience;
import com.chitra.domain.GitRepository;
import com.chitra.resource.ExperienceResource;
import com.chitra.resource.GitRepositoryResource;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertToResourceService {

    public List<ExperienceResource> convertToExperienceResourceList(List<Experience> allExperience){
        return allExperience.stream()
                .map(ExperienceResource::new)
                .collect(Collectors.toList());
    }

    public ExperienceResource convertToExperienceResource(Experience experience){
        return new ExperienceResource(experience);
    }

    public List<GitRepositoryResource> convertToGitRepoResourceList(List<GitRepository> allRepo){
        return allRepo.stream()
                .map(GitRepositoryResource::new)
                .collect(Collectors.toList());
    }

    public GitRepositoryResource convertToGitRepoResource(GitRepository gitRepository){
        return new GitRepositoryResource(gitRepository);
    }
}
