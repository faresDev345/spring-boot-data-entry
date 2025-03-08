package com.app.data.entry.movies.writer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.data.entry.movies.entities.Movie;
import com.app.data.entry.movies.repo.MovieRepository;

@Component
public class MovieWriter implements ItemWriter<Movie> {

    @Autowired
    private MovieRepository movieRepository;

    public void write(List<? extends Movie> movies) throws Exception {
        // Save all movies to the database
        movieRepository.saveAll(movies);
    }

	@Override
	public void write(Chunk<? extends Movie> movies) throws Exception {

		  List<Movie> maList = new ArrayList<Movie>();
		  for (Movie movi : movies) {
			  Movie myMovie = new Movie();
			  myMovie = movieRepository.findByNameAndYear(movi.getName(), movi.getYear());
		  	  if ((myMovie != null) && (myMovie.getId() != null) && (myMovie.getId() > 0)) {
			    System.out.println("Movie : " +  movi.getName() + " - " + movi.getName() + " " + movi.getName() +  " : " + movi.getYear() + " déjà enregistré ");   }
		  	  else { 
		  		  maList.add(movi);
		  	  }
		  } 
        movieRepository.saveAll(maList);
    }
}