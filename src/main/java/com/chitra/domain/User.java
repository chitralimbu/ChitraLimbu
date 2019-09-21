package com.chitra.domain;

import java.util.Set;

import javax.validation.constraints.NotBlank;

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
	@NotBlank(message="Username cannot be blank")
	private String username;
	private String password;
	private String fullname;
	private String email;
	@DBRef
	private Set<Role> roles;

}
