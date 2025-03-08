package com.app.data.entry.movies.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.app.data.entry.movies.entities.MovieDTO;

@Component
public class MovieMapper implements RowMapper<MovieDTO> {
	String formatDate = "yyyy-MM-dd";
    @Override
    public MovieDTO mapRow(ResultSet csvRecord, int rowNum) throws SQLException {
    	MovieDTO movie = new MovieDTO();
    	 movie.setName(csvRecord.getString("name"));
         movie.setRating(csvRecord.getString("rating"));
         movie.setGenre(csvRecord.getString("genre"));
         movie.setYear(csvRecord.getInt("year"));
         movie.setReleased(csvRecord.getString("released"));
         movie.setScore(Double.parseDouble(csvRecord.getString("score")));
         movie.setVotes( csvRecord.getInt("votes"));
         movie.setDirector(csvRecord.getString("director"));
         movie.setWriter(csvRecord.getString("writer"));
         movie.setStar(csvRecord.getString("star"));
         movie.setCountry(csvRecord.getString("country"));
         movie.setBudget(csvRecord.getString("budget"));
         movie.setGross(csvRecord.getString("gross"));
         movie.setCompany(csvRecord.getString("company"));
         movie.setRuntime(csvRecord.getString("runtime"));


        return movie;
    }
    Date convertStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);  
        Date mdate = formatter.parse(dateString);
        return mdate;
    }
}
