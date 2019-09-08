package com.chitra.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection="user")
public class User{
	
	@Id
	private int id;
	@NonNull
	@NotBlank(message="Username cannot be blank")
	private String username;
	@NonNull
	@NotBlank(message="Password cannot be blank")
	private String password;
	@NonNull
	private String fullname;
	@NonNull
	@NotBlank(message="e-mail cannot be blank")
	private String email;
	
	@DBRef
	private Set<Role> roles;

}
