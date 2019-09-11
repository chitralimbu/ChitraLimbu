package com.chitra.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@RequiredArgsConstructor
@Document(collection="user")

public class User{
	
	@Id
	private int id;
	private final String username;
	private final String password;
	private final String fullname;
	private final String email;
	@DBRef
	private Set<Role> roles;

}
