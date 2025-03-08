package com.app.data.entry.films.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.data.entry.films.entities.Film;
@Repository
public interface FilmRepository extends CrudRepository<Film, Long> {
	   List<Film> findByTitle(String name	);
	    long countByTitle(String name	);
}
