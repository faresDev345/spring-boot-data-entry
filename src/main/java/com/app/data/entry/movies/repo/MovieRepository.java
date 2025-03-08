package com.app.data.entry.movies.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.data.entry.movies.entities.Movie;
@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
	   List<Movie> findByName(String name	);
	    long countByName(String name	);
	    Movie findByNameAndYear(String name, int year);
}
