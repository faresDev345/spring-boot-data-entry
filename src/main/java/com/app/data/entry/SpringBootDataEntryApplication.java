package com.app.data.entry;
 
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.data.entry.jobs.JobExplorerLister;
import com.app.data.entry.jobs.JobLister;
import com.app.data.entry.jobs.JobRunner;

import lombok.extern.log4j.Log4j2;
@Log4j2
@SpringBootApplication
public class SpringBootDataEntryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataEntryApplication.class, args);
	}
	

		 
	    @Autowired(required = true)
	    private JobRunner jobRun ;
	    @Autowired(required = true)
	    private JobLister jobLister ;
	    @Autowired(required = true)
	    private JobExplorerLister jobExplorLister ;
	    @Override
	    public void run(String... args) throws Exception {

	    	
	    	log.info("################## List 1 --- List of jobs in project .............  "  );
	    	jobLister.listJobs();
	    	

	    	
	    	log.info("################## List 2 --- List of jobs in project .............  "  );
	    	jobExplorLister.listJobs();
	    	
	    	
	    	
	    	log.info("runBatchJob --- triggered .............  " + jobRun.getJobName());
			
	        jobRun.runJob();
	   
	        log.info("run Batch Job --- triggered ............. End " );
	    }
	    
		 
		//Bean
		public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
			return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
		}

	}
