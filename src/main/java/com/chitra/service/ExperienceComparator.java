package com.chitra.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import com.chitra.domain.Experience;

public class ExperienceComparator implements Comparator<Experience>{ 
	
	@Override
	public int compare(Experience one, Experience two) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		String oneDate = String.format("01 %s", one.getEndDate()).trim();
		String twoDate = String.format("01 %s", two.getEndDate()).trim();
		LocalDate dateOne = one.getEndDate().equalsIgnoreCase("present") ? LocalDate.now() : LocalDate.parse(oneDate, formatter);
		LocalDate dateTwo = two.getEndDate().equalsIgnoreCase("present") ? LocalDate.now() : LocalDate.parse(twoDate, formatter);
		return dateTwo.compareTo(dateOne);
	}

}
