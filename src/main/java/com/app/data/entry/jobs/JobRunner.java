
package com.app.data.entry.jobs;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
  public class JobRunner {
  
  @Autowired JobLauncher jobLauncher;
  
	//Autowired(required = true) // job6
	//Qualifier("jobPerson")  //job6
	//private Job jobPerson; 
  
	//@Autowired(required = true) // job6
	//@Qualifier("jobTuto")  //job6
	//private Job myJob; 
	
  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
  
  
  //Scheduled(fixedRate = 5000) //Scheduled(fixedDelay = 5000, initialDelay =5000) 
public void scheduleByFixedRate() throws Exception {
  System.out.println("Batch job starting");
  JobParameters jobParameters = new JobParametersBuilder() .addString("time",
  format.format(Calendar.getInstance().getTime())).toJobParameters();
//  jobLauncher.run(myJob, jobParameters);
  System.out.println("Batch job executed successfully\n");
  } 


@Autowired
private ApplicationContext context;

@Value("${batch.job.jobName}")
private String jobName;

public String getJobName() {
	return jobName;
}

public void setJobName(String jobName) {
	this.jobName = jobName;
}

public void runJob() {
    try {
        // Récupère le job spécifié depuis le contexte en utilisant le nom du job
    	log.info(" .............   Récupère le job spécifié depuis le contexte en utilisant le nom du job " + jobName);
        Job job = context.getBean(jobName, Job.class);
        jobLauncher.run(job,  getNewJobParams());
        System.out.println("Job " + jobName + " lancé avec succès.");
    	log.info(" .............  Job " + jobName + "lancé avec succès");
    } catch (Exception e) {
    	log.info(" .............  Job " + "Erreur lors de l'exécution du job " + jobName + ": " + e.getMessage());
        System.err.println("Erreur lors de l'exécution du job " + jobName + ": " + e.getMessage());
        e.printStackTrace();
    }
}

private JobParameters getNewJobParams() {
	JobParameters jbParams = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis())
			              .addString("jobId", String.valueOf(UUID.randomUUID())).toJobParameters();
 
	return jbParams;
}


public void invokeJob(String jobName, JobParameters jobParameters) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
    var jobToStart = context.getBean(jobName, Job.class);
    jobLauncher.run(jobToStart, jobParameters);
}


}
