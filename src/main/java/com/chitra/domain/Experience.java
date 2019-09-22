package com.chitra.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@Document(collection="experience")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
	@Id
	private String id;
	@NotBlank(message="Title canot be blank")
	private String title;
	@NotBlank(message="StartDate canot be blank")
	private String startDate;
	@NotBlank(message="End Date canot be blank (Present if still working there)")
	private String endDate;
	@NotBlank(message="Organisation canot be blank")
	private String organisation;
	private String description;
	@DBRef
	private List<Tasks> tasks;
}
