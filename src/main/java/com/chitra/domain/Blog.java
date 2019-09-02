package com.chitra.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
//is a mongodb collection
public class Blog {

	private final int id;
	private final String title;
	private final String topic;
	private final String body;
	private final String image;
	
}
