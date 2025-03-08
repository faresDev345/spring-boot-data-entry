package com.app.data.entry.jobs;
import java.util.Collection;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class JobLister {

    @Autowired
    private JobRegistry jobRegistry;

    @PostConstruct
    public void listJobs() {
        Collection<String> jobNames = jobRegistry.getJobNames();
        System.out.println("Defined Jobs:");
        for (String jobName : jobNames) {
            System.out.println("- " + jobName);
        }
    }
}