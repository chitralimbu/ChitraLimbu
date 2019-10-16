package com.chitra.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Document(collection="repository")
public class Repository {
	@NotNull
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String full_name;
	
	private boolean enabled;
	
	@DBRef
	List<GitObject> allItems;
}
