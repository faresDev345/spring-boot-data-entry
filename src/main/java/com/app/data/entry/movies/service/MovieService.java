package com.app.data.entry.movies.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.data.entry.movies.entities.Movie;
import com.app.data.entry.movies.repo.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void importData(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            List<Movie> movies = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                Movie movie = new Movie();
                movie.setName(csvRecord.get("name"));
                movie.setRating(csvRecord.get("rating"));
                movie.setGenre(csvRecord.get("genre"));
                movie.setYear(Integer.parseInt(csvRecord.get("year")));
                movie.setReleased(csvRecord.get("released"));
                movie.setScore(Double.parseDouble(csvRecord.get("score")));
                movie.setVotes(Integer.parseInt(csvRecord.get("votes")));
                movie.setDirector(csvRecord.get("director"));
                movie.setWriter(csvRecord.get("writer"));
                movie.setStar(csvRecord.get("star"));
                movie.setCountry(csvRecord.get("country"));
                movie.setBudget(csvRecord.get("budget"));
                movie.setGross(csvRecord.get("gross"));
                movie.setCompany(csvRecord.get("company"));
                movie.setRuntime(csvRecord.get("runtime"));

                movies.add(movie);
            }

            movieRepository.saveAll(movies);
        }
    }
}