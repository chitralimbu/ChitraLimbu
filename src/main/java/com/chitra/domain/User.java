package com.chitra.domain;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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
	@Pattern(regexp="((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})", message = "Password must be between 8-40 characters, Must include one Uppercase letter and one of [@,#,$,%,!]")
	private String password;
	@NotBlank(message="Email cannot be blank")
	@Email(message = "Please enter a valid email")
	private String email;
	@DBRef
	private Set<Role> roles;
}
