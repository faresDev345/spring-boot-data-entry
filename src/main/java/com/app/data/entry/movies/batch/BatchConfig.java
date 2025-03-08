package com.app.data.entry.movies.batch; 

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.JobRegistrySmartInitializingSingleton;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.app.data.entry.movies.entities.Movie;
import com.app.data.entry.movies.entities.MovieDTO;
import com.app.data.entry.movies.listener.MovieWriterListener;
import com.app.data.entry.movies.processor.MovieProcessor;
import com.app.data.entry.movies.reader.MovieReader;
import com.app.data.entry.movies.writer.MovieWriter;

import lombok.extern.log4j.Log4j2;

//@Configuration
//@EnableBatchProcessing 
@Log4j2
public class BatchConfig {
	//@Bean
	public JobRegistrySmartInitializingSingleton jobRegistrySmartInitializingSingleton(JobRegistry jobRegistry) {
		return new JobRegistrySmartInitializingSingleton(jobRegistry);
	}

	
	 // JdbcTemplates
    @Bean
    public JdbcTemplate sourceJdbcTemplate(@Qualifier("sourceDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcTemplate destinationJdbcTemplate(@Qualifier("destinationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

 // Job et Steps (nouvelle API)
    @Bean("copyMovieJob")
    @Qualifier("copyMovieJob")
    public Job copyMovieJob(JobRepository jobRepository, Step copyCategoriesStep, Step copyMoviesStep) {
        return new JobBuilder("copyMovieJob", jobRepository)
                .start(copyCategoriesStep)
                .next(copyMoviesStep)
                .build();
    }

   


    // Step : Copie des movies

    @Bean
    @Qualifier("copyMoviesStep")
    public Step copyMoviesStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        MovieReader movieReader,
        MovieProcessor movieProcessor,
        MovieWriter movieWriter,
        MovieWriterListener movieWriterListener
        
    ) {
        return new StepBuilder("copyMoviesStep", jobRepository)
                .<MovieDTO, Movie>chunk(10, transactionManager)
                .reader(movieReader)
                .processor(movieProcessor)
                .writer(movieWriter)
                .listener(movieWriterListener)
                .build();
    }
  

    // Processors
   

   // @Bean
    @StepScope
    public ItemProcessor<Movie, Movie> movieProcessor(
        @Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate,
        @Value("#{jobExecutionContext['categoryMappings']}") Map<Long, Long> categoryMappings
    ) {
        return movie -> {
            String sql = "SELECT id FROM movie WHERE name = ? AND year = ?";
            List<Long> existingIds = destinationJdbcTemplate.queryForList(sql, Long.class, movie.getName(), movie.getYear());
            if (!existingIds.isEmpty()) { // Filtre les movies existants
	            log.info("Filtre les movies existants : " + movie.getName());
	            return null;
            }
			return movie;  
        };
    }

    // Writers
 

    //@Bean
    //@StepScope
    public JdbcBatchItemWriter<Movie> movieWriter(@Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate) {
        return new JdbcBatchItemWriterBuilder<Movie>()
                .sql("INSERT INTO movie (title, release_year, category_id) VALUES (:title, :releaseYear, :categoryId)")
                .beanMapped()
                .dataSource(destinationJdbcTemplate.getDataSource())
                .build();
    }
}