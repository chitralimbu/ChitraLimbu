package com.chitra.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
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
@Document(collection="gitDirTree")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitDirTree {
	@Id
	@NotNull
	private String sha;
	@NotNull
	private String path;
	@NotNull
	private String type;
	@NotNull
	private boolean enabled = true;
	
	private String raw;
}
