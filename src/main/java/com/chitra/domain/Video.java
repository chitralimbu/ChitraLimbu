package com.chitra.domain;

import java.io.InputStream;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Video {
	private String title;
	private InputStream stream;
}
