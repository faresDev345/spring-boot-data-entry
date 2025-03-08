package com.app.data.entry.films.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.app.data.entry.films.entities.FilmDTO;

@Component
public class FilmMapper implements RowMapper<FilmDTO> {
	String formatDate = "yyyy-MM-dd";
    @Override
    public FilmDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	FilmDTO film = new FilmDTO();
        film.setId(rs.getLong("id"));
        film.setTitle(rs.getString("title"));
        film.setLast_update( rs.getString("last_update"));
      
        film.setDescription(rs.getString("description"));
        film.setLength(rs.getInt("length"));
        film.setRating(rs.getString("rating"));
        film.setRelease_year(rs.getInt("release_year"));
        film.setRental_rate(rs.getDouble("rental_rate"));
        film.setReplacement_cost(rs.getDouble("replacement_cost"));
        film.setSpecial_features(rs.getString("special_features"));

        return film;
    }
    Date convertStringToDate(String dateString, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);  
        Date mdate = formatter.parse(dateString);
        return mdate;
    }
}
