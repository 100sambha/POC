package com.aithentic.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.aithentic.tasks.TaskOne;
import com.aithentic.tasks.TaskTwo;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	
	public BatchConfig() {
		super();
	}

	public BatchConfig(JobBuilderFactory jobs, StepBuilderFactory steps) {
		super();
		this.jobs = jobs;
		this.steps = steps;
	}

	public JobBuilderFactory getJobs() {
		return jobs;
	}

	public void setJobs(JobBuilderFactory jobs) {
		this.jobs = jobs;
	}

	public StepBuilderFactory getSteps() {
		return steps;
	}

	public void setSteps(StepBuilderFactory steps) {
		this.steps = steps;
	}

	@Bean
	public Step stepOne()
	{
		return steps.get("stepOne")
				.tasklet(new TaskOne())
				.build();
	}
	
	@Bean
	public Step stepTwo()
	{
		return steps.get("stepTwo")
				.tasklet(new TaskTwo())
				.build();
	}
	
	@Bean(name = "demoJobOne")
	public Job demoJobOne() {
		return jobs.get("demoJobOne")
				.start(stepOne())
				.next(stepTwo())
				.build();
	}
	
	@Bean(name = "demoJobTwo")
	public Job demoJobTwo() {
		return jobs.get("demoJobTwo")
				.start(stepOne())
				.build();
	}
}