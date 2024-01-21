package com.behrouztakhti.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * JobCompletionListener class.
 * @author behrouz.takhti@gmail.com
 */
public class JobCompletionListener implements JobExecutionListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobCompletionListener.class);


    /**
     * We can create listener in order to listen the job and adding some functionality around it.
     * This method listens the job and adds a functionality after finishing it.
     * @param jobExecution an object of JobExecution.
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("BATCH JOB COMPLETED SUCCESSFULLY");
        }
    }


}
