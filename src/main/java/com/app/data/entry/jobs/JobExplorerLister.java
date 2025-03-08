package com.app.data.entry.jobs;

import java.util.List;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JobExplorerLister {

	@Autowired
    private JobExplorer jobExplorer;

    @PostConstruct
    public void listJobs() {
        try {
            List<String> jobNames = jobExplorer.getJobNames();
            System.out.println("Defined Jobs (using JobExplorer):");
            for (String jobName : jobNames) {
                System.out.println("- " + jobName);
            }
        } catch (Exception e) {
            System.err.println("Error in JobExplorerLister initialization:");
            e.printStackTrace();
        }
    }
}