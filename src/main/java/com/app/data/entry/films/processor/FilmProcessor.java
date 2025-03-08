package com.app.data.entry.films.processor;

import org.springframework.batch.item.ItemProcessor;

import com.app.data.entry.films.entities.Film;
import com.app.data.entry.films.entities.FilmDTO;

//@Component
public class FilmProcessor implements ItemProcessor<FilmDTO, Film> {

	@Override
	public Film process(FilmDTO item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	} 
	
}

	 
