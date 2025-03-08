package com.app.data.entry.movies.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.app.data.entry.movies.entities.Movie;
import com.app.data.entry.movies.entities.MovieDTO;


@Component
public class MovieProcessor implements ItemProcessor<MovieDTO, Movie> {

    @Override
    public Movie process(MovieDTO movieDTO) throws Exception {
        // Convert MovieDTO to Movie entity
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setRating(movieDTO.getRating());
        movie.setGenre(movieDTO.getGenre());
        movie.setYear(movieDTO.getYear());
        movie.setReleased(movieDTO.getReleased());
        movie.setScore(movieDTO.getScore());
        movie.setVotes(movieDTO.getVotes());
        movie.setDirector(movieDTO.getDirector());
        movie.setWriter(movieDTO.getWriter());
        movie.setStar(movieDTO.getStar());
        movie.setCountry(movieDTO.getCountry());
        movie.setBudget(movieDTO.getBudget());
        movie.setGross(movieDTO.getGross());
        movie.setCompany(movieDTO.getCompany());
        movie.setRuntime(movieDTO.getRuntime());
        return movie;
    }
}
	 
