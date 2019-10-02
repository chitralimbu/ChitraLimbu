package com.chitra.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Document(collection="experience")
@Getter
@Setter
public class Experience {
	@Id
	private String id;
	private String title;
	private String startDate;
	private String endDate;
	private String organisation;
	private String description;
	private List<String> tasks;
	
	public Experience() {
		
	}
	
	public Experience(String id, String title, String startDate, String endDate, String organisation, String description) {
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.organisation = organisation;
		this.description = description;
	}
}
