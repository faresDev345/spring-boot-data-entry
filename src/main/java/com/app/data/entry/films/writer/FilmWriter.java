package com.app.data.entry.films.writer;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.app.data.entry.films.entities.Film;

//@Component
public class FilmWriter implements ItemWriter<Film>{

	@Override
	public void write(Chunk<? extends Film> chunk) throws Exception {
		// TODO Auto-generated method stub
		
	} 
	
}
