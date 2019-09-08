package com.chitra.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.chitra.domain.User;
import lombok.Data;

@Data
public class RegistrationForm {
	
	private String username;
	private String password;
	private String fullname;
	private String email;

	public User toUser(PasswordEncoder passwordEncoder) {
		User user = new User(username, passwordEncoder.encode(password), fullname, email);
		user.setId(user.hashCode());
		return user;
	}

}

