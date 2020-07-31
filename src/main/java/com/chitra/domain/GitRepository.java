package com.chitra.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
@Document(collection="gitrepository")
public class GitRepository {
	@Id
	@NonNull
	private String id;
	@NonNull
	private String name;
	@NonNull
	private String full_name;
	@NonNull
	private String html_url;
	@NonNull
	private String description;
	
	private boolean igonre = false;

	@DBRef
	private List<GitRepositoryContents> allContents;
}

