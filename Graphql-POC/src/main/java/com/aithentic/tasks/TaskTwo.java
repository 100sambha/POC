package com.aithentic.tasks;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class TaskTwo implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
	{
		System.out.println("=======> Second Task Started <=======");
		
		Thread.sleep(10);
		System.out.println("=======> Second Task Ended <=======");
		
		return RepeatStatus.FINISHED;
	}

}
