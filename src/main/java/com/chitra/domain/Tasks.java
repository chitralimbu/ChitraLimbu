package com.chitra.domain;

import org.springframework.data.annotation.Id;
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
@Document(collection="task")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tasks {
	@Id
	private String id;
	private String task;
}
