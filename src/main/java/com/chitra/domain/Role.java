package com.chitra.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection="role")
public class Role {
	
	@Id
	private String _id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING)
	
	private String role;
}
