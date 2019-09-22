package com.chitra.media.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Document(collection="photos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

	@Id
	private String id;
	private String title;
	private Binary image;
}
