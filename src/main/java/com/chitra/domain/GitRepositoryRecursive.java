package com.chitra.domain;

import org.springframework.data.annotation.Id;
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
@Document(collection="gitrepositoryrecursive")
public class GitRepositoryRecursive {
	@NonNull
	private String path;
	@NonNull
	private String type;
	@Id
	@NonNull
	private String sha;
	@NonNull
	private String url;
	private String raw;
	private String code = "no code";
}
