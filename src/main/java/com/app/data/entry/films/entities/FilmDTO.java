package com.app.data.entry.films.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor

public class FilmDTO {
	
	private Long id;
	private String title;
	private String description;
	private int release_year;
	private double rental_rate;
	private int length;
	private double replacement_cost;
	private String rating;
	private String special_features;
	private String last_update;
	 
	
}