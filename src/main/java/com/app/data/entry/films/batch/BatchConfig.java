package com.app.data.entry.films.batch; 

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

import com.app.data.entry.films.entities.Category;
import com.app.data.entry.films.entities.Film;
import com.app.data.entry.films.listener.CategoryWriterListener;
import com.app.data.entry.films.listener.FilmWriterListener;
import com.app.data.entry.films.processor.CategoryProcessor;
import com.app.data.entry.films.writer.CategoryWriter;

import lombok.extern.log4j.Log4j2;

@Configuration
@EnableBatchProcessing 
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
    @Bean("copyFilmJob")
    public Job copyFilmJob(JobRepository jobRepository, Step copyCategoriesStep, Step copyFilmsStep) {
        return new JobBuilder("copyFilmJob", jobRepository)
                .start(copyCategoriesStep)
                .next(copyFilmsStep)
                .build();
    }

    // Step 1: Copie des catégories
    @Bean
    public Step copyCategoriesStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Category> categoryReader,
        CategoryProcessor categoryProcessor,
        CategoryWriter categoryWriter
    ) {
        return new StepBuilder("copyCategoriesStep", jobRepository)
                .<Category, Category>chunk(10, transactionManager)
                .reader(categoryReader)
                .processor(categoryProcessor)
                .writer(categoryWriter)
                .listener(new CategoryWriterListener())
                .build();
    }


    // Step 2: Copie des films

    @Bean
    public Step copyFilmsStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        ItemReader<Film> filmReader,
        ItemProcessor<Film, Film> filmProcessor,
        ItemWriter<Film> filmWriter
    ) {
        return new StepBuilder("copyFilmsStep", jobRepository)
                .<Film, Film>chunk(10, transactionManager)
                .reader(filmReader)
                .processor(filmProcessor)
                .writer(filmWriter)
                .listener(new FilmWriterListener())
                .build();
    }
    // Readers
    @Bean
    @StepScope 
    public JdbcCursorItemReader<Category> categoryReader(@Qualifier("sourceDataSource") DataSource sourceDataSource) {
        return new JdbcCursorItemReaderBuilder<Category>()
                .name("categoryReader")
                .sql("SELECT id, name FROM category")
                .rowMapper((rs, rowNum) -> new Category(rs.getLong("id"), rs.getString("name")))
                .dataSource(sourceDataSource)
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Film> filmReader(@Qualifier("sourceDataSource") DataSource sourceDataSource) {
        return new JdbcCursorItemReaderBuilder<Film>()
                .name("filmReader")
                .sql("SELECT id, title, release_year, category_id FROM film")
                .rowMapper((rs, rowNum) -> new Film(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getInt("release_year"),
                    rs.getLong("category_id")
                ))
                .dataSource(sourceDataSource)
                .build();
    }


    // Processors
    //@Bean
    @StepScope
    public ItemProcessor<Category, Category> categoryProcessorOld(
        @Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate
    ) {
        return category -> {
            String sql = "SELECT id FROM category WHERE name = ?";
            List<Long> existingIds = destinationJdbcTemplate.queryForList(sql, Long.class, category.getName());
            return existingIds.isEmpty() ? category : null; // Filtre les catégories existantes
        };
    }

    @Bean
    @StepScope
    public ItemProcessor<Film, Film> filmProcessor(
        @Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate,
        @Value("#{jobExecutionContext['categoryMappings']}") Map<Long, Long> categoryMappings
    ) {
        return film -> {
            String sql = "SELECT id FROM film WHERE title = ? AND release_year = ?";
            List<Long> existingIds = destinationJdbcTemplate.queryForList(sql, Long.class, film.getTitle(), film.getReleaseYear());
            if (!existingIds.isEmpty()) { // Filtre les films existants
	            log.info("Filtre les films existants : " + film.getTitle());
	            return null;
            } else {

                // Mise à jour de category_id avec l'ID de destination
                film.setCategoryId(categoryMappings.get(film.getCategoryId()));
                return film;
            }
        };
    }

    // Writers
    //Bean
    @StepScope
    public JdbcBatchItemWriter<Category> categoryWriterOld(@Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate) {
        return new JdbcBatchItemWriterBuilder<Category>()
                .sql("INSERT INTO category (name) VALUES (:name)")
                .beanMapped()
                .dataSource(destinationJdbcTemplate.getDataSource())
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Film> filmWriter(@Qualifier("destinationJdbcTemplate") JdbcTemplate destinationJdbcTemplate) {
        return new JdbcBatchItemWriterBuilder<Film>()
                .sql("INSERT INTO film (title, release_year, category_id) VALUES (:title, :releaseYear, :categoryId)")
                .beanMapped()
                .dataSource(destinationJdbcTemplate.getDataSource())
                .build();
    }
}