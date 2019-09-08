package com.chitra.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.chitra.domain.User;
import lombok.Data;

@Data
public class RegistrationForm {
	
	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phone;

	public User toUser(PasswordEncoder passwordEncoder) {
		User user = new User(username, passwordEncoder.encode(password), 
				fullname, street, city, state, zip, phone);
		user.setId(user.hashCode());
		return user;
	}

}

