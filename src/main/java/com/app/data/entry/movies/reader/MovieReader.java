package com.app.data.entry.movies.reader;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.app.data.entry.comun.Utiles;
import com.app.data.entry.movies.entities.MovieDTO;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component 
	public class MovieReader extends FlatFileItemReader<MovieDTO> 	 implements RowMapper<MovieDTO>	{
		


		@Value("${file.input.movie}")
	 	private String  file_path;
	 	@Autowired
	 	private ResourceLoader resourceLoader;
	   
	    @Bean
	    public FlatFileItemReader<MovieDTO> reader(@Value("${file.input.movie}") String  file_path) throws IOException {
	    	
	    	log.info("####################  >>1. file path  : "+file_path);
	        FlatFileItemReader<MovieDTO> itemReader = new FlatFileItemReader<>();
	        itemReader.setResource(Utiles.getPropertyFile(resourceLoader,file_path));
	        itemReader.setName("csvReaderMovie");
	        itemReader.setEncoding("UTF-8");
	        itemReader.setLinesToSkip(1); 
	        itemReader.setLineMapper(lineMapperMovie());
	        return itemReader;
	    }
	    @Bean
	    private LineMapper<MovieDTO> lineMapperMovie() {
	        DefaultLineMapper<MovieDTO> lineMapper = new DefaultLineMapper<>();

	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setDelimiter(",");
	        lineTokenizer.setStrict(true); 
	        lineTokenizer.setNames("name", "rating" , "genre", "rating" , "year", "released" ,"score",  "votes" ,"director","writer" ,"star" , "country" ,"budget" , "gross" ,"runtime"     );
	        BeanWrapperFieldSetMapper<MovieDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
	        fieldSetMapper.setTargetType(MovieDTO.class);

	        lineMapper.setLineTokenizer(lineTokenizer);
	        lineMapper.setFieldSetMapper(fieldSetMapper);
	        return lineMapper;

	    }
	    public MovieReader(ResourceLoader resourceLoader,@Value("${file.input.movie}") String  file_path) throws IOException {
	        // Set the resource (CSV file)
	    	log.info("####################  >>2. file path : "+file_path);
	        this.setResource(resourceLoader.getResource(file_path));
	        this.setLinesToSkip(1);
	        // Configure the LineMapper
	        DefaultLineMapper<MovieDTO> lineMapper = new DefaultLineMapper<>();

	        // Configure the LineTokenizer
	       // [name,rating,genre,year,released,score,votes,director,writer,star,country,budget,gross,company,"runtime,,"]
	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
	        lineTokenizer.setNames(new String[]{
	            "name", "rating", "genre", "year", "released", "score", "votes",
	            "director", "writer", "star", "country", "budget", "gross", "company", "runtime"
	        });
	        lineTokenizer.setDelimiter(","); // CSV delimiter
	        lineMapper.setLineTokenizer(lineTokenizer);

	        // Configure the FieldSetMapper
	        lineMapper.setFieldSetMapper(new MovieFieldSetMapper());

	        // Set the LineMapper
	        this.setLineMapper(lineMapper);
	    }

	    // Custom FieldSetMapper to map rows to MovieDTO
	    private static class MovieFieldSetMapper implements FieldSetMapper<MovieDTO> {
	        @Override
	        public MovieDTO mapFieldSet(FieldSet fieldSet) throws BindException {
	            MovieDTO movie = new MovieDTO();
	            movie.setName(fieldSet.readString("name"));
	            movie.setRating(fieldSet.readString("rating"));
	            movie.setGenre(fieldSet.readString("genre"));
	            movie.setYear(fieldSet.readInt("year"));
	            movie.setReleased(fieldSet.readString("released"));
	            movie.setScore(fieldSet.readDouble("score"));
	            movie.setVotes(fieldSet.readInt("votes"));
	            movie.setDirector(fieldSet.readString("director"));
	            movie.setWriter(fieldSet.readString("writer"));
	            movie.setStar(fieldSet.readString("star"));
	            movie.setCountry(fieldSet.readString("country"));
	            movie.setBudget(fieldSet.readString("budget"));
	            movie.setGross(fieldSet.readString("gross"));
	            movie.setCompany(fieldSet.readString("company"));
	            movie.setRuntime(fieldSet.readString("runtime"));
	            return movie;
	        }
	    }
		@Override
		public MovieDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			 MovieDTO movie = new MovieDTO();
			 return new MovieDTO(
            		 rs.getString("name"),
                     rs.getString("rating"),
                     rs.getString("genre"),
                     rs.getInt("year") ,
                     rs.getString("released"),
                     rs.getDouble("score"),
                     rs.getInt("votes"),
                     rs.getString("director") ,
                     rs.getString("writer"),
                     rs.getString("star"),
                     rs.getString("country"),
                     rs.getString("budget") ,
                     rs.getString("gross"),
                     rs.getString("company"),
                     rs.getString("runtime") 
            );  
		}
	}
