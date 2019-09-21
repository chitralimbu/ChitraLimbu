package com.chitra.domain;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RegistrationForm {
	
	@NotBlank(message="Username cannot be blank")
	private String username;
	
	@NotBlank(message="Password cannot be blank")
	private String password;
	
	@NotBlank(message="Password cannot be blank")
	private String fullname;
	
	@NotBlank(message="E-mail cannot be blank")
	private String email;
}
