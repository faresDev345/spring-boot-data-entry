package com.app.data.entry.movies.entities;

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

public class MovieDTO {
	
	 public MovieDTO (String name, String rating, String genre, int year, String released, double score, int votes,
				String director, String writer, String star, String country, String budget, String gross, String company,
				String runtime) {
			super();
			this.name = name;
			this.rating = rating;
			this.genre = genre;
			this.year = year;
			this.released = released;
			this.score = score;
			this.votes = votes;
			this.director = director;
			this.writer = writer;
			this.star = star;
			this.country = country;
			this.budget = budget;
			this.gross = gross;
			this.company = company;
			this.runtime = runtime;
		}
	private Long id;

	    private String name;
	    private String rating;
	    private String genre;
	    private int year;
	    private String released;
	    private double score;
	    private int votes;
	    private String director;
	    private String writer;
	    private String star;
	    private String country;
	    private String budget;
	    private String gross;
	    private String company;
	    private String runtime;
	
}