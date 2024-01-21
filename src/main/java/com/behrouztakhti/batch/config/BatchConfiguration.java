package com.behrouztakhti.batch.config;

import com.behrouztakhti.batch.domain.Country;
import com.behrouztakhti.batch.dto.CountryDTO;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


/**
 * This is configuration class for Spring batch.
 * We need to create Reader in order to read a file such as CSV file.
 * We need to create Processor in order to process input data after reading.
 * We need to create Writer in order to write in database or in another file.
 * We can create listener in order to listen the job and adding some functionality  around it.
 * In this sample project we use Scheduler in order to Schedule the job, so we need annotate this class with @EnableScheduling.
 * @author behrouz.takhti@gmail.com
 */

@Configuration
@EnableScheduling
public class BatchConfiguration {
    private JobLauncher jobLauncher;
    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    public DataSource dataSource;

    public BatchConfiguration(JobLauncher jobLauncher, JobRepository jobRepository,
                              PlatformTransactionManager transactionManager, DataSource dataSource) {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.dataSource = dataSource;
    }

    /**
     * Get csv-file path
     */
    @Value("${file.input}")
    private String fileInput;


    /**
     * create a bean for reader.
     */
    @StepScope
    @Bean
    public FlatFileItemReader<CountryDTO> reader() {
        return new FlatFileItemReaderBuilder<CountryDTO>()
                .name("countryItemReader")
                .resource(new ClassPathResource(fileInput))
                .delimited()
                .names(new String[]{"unloCode", "name"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(CountryDTO.class);
                }})
                .linesToSkip(1)
                .build();
    }

    /**
     * create a bean for processor.
     * @see CountryItemProcessor
     */
    @Bean
    public ItemProcessor processor() {
        return new CountryItemProcessor();
    }

    /**
     * create a bean for writer.
     */
    @Bean
    public JdbcBatchItemWriter<Country> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Country>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO Country (unloCode, name) VALUES (:unloCode, :name)")
                .dataSource(dataSource)
                .build();
    }

    /**
     * create a bean for listener.
     * @see JobCompletionListener
     */
    @Bean
    public JobExecutionListener listener() {
        return new JobCompletionListener();
    }


    /**
     * create a  bean for step which configure reader, processor, writer.
     */
    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<CountryDTO, Country> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer(dataSource))
                .build();
    }


    /**
     * create a job which configure step and listener.
     */
    @Bean
    public Job fetchCountriesAndSaveThemInDatabaseJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("fetchCountriesAndSaveThemInDatabaseJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step(jobRepository, transactionManager))
                .end()
                .build();
    }


    /**
     * The scheduler will schedule the job after every 1 minute.
     * We also send jobTime as a jobParameter which it can be used in reader or writer.
     * */
    @Scheduled(fixedRate = 60000)
    public void launchJob() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder().addLong("jobTime", System.currentTimeMillis()).toJobParameters();
        jobLauncher.run(fetchCountriesAndSaveThemInDatabaseJob(jobRepository, transactionManager), jobParameters);
    }




}
