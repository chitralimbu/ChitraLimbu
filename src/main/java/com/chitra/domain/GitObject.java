package com.chitra.domain;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection="gitobject")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitObject {
	
	@Id
	@NotNull
	private String sha;
	@NotNull
	private String name;
	@NotNull
	private String path;
	@NotNull
	private String html_url;
	@NotNull
	private String git_url;
	@NotNull
	private String download_url;
	@NotNull
	private String type;
	@DBRef
	private List<GitDirTree> gitDirTree;
}
