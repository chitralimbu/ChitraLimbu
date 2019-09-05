package com.chitra.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection="blog")
@Getter
@Setter
public class Blog implements Serializable{
	
	private static final long serialVersionUID = 6869144070018235511L;
	
	@Id
	private int id;
	
	@NotBlank(message="Title cannot be blank")
	private String title;
	
	@NotBlank(message="Topic cannot be blank")
	private String topic;
	private String body;
	private String image = "default.jpg";	
	private MultipartFile imageFile;
	
}
