package com.app.data.entry.films.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	 

	@Column(name = "title")
	@NonNull 
    private String title;
	@Column
    private int releaseYear;
	@Column
    private Long categoryId;
}