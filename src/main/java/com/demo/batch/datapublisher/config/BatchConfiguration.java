package com.demo.batch.datapublisher.config;

import com.demo.batch.datapublisher.entity.Employee;
import com.demo.batch.datapublisher.mapper.EmployeeDtoMapper;
import com.demo.batch.datapublisher.model.EmployeeDto;
import com.demo.batch.datapublisher.processor.EmployeeProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    DataSource dataSource;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private final Resource outputResource = new FileSystemResource("output/processed_data.csv");

    /*@Bean
    public JpaPagingItemReader<Employee> reader() {
        return new JpaPagingItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select e.firstName, e.lastName, e.city from Employee e")
                .transacted(true)
                .pageSize(10)
                .build();
    }*/


    @Bean
    public ItemReader<EmployeeDto> reader(){
        JdbcCursorItemReader<EmployeeDto> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT first_name, last_name, city FROM employee");
        reader.setRowMapper(new EmployeeDtoMapper());

        return reader;
    }

    @Bean
    public EmployeeProcessor processor() {
        return new EmployeeProcessor();
    }

    @Bean
    public FlatFileItemWriter<EmployeeDto> writer()
    {
        //Create writer instance
        FlatFileItemWriter<EmployeeDto> writer = new FlatFileItemWriter<>();
        //Set output file location
        writer.setResource(outputResource);
        //All job repetitions should "append" to same output file
        writer.setAppendAllowed(true);
        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<EmployeeDto>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<EmployeeDto>() {
                    {
                        setNames(new String[] { "firstName", "lastName", "city" });
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                                .incrementer(new RunIdIncrementer())
                                .listener(listener)
                                .flow(step1)
                                .end()
                                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter<EmployeeDto> writer) {
        return stepBuilderFactory.get("step1")
                                 .<EmployeeDto, EmployeeDto> chunk(10)
                                 .reader(reader())
                                 .processor(processor())
                                 .writer(writer)
                                 .build();
    }

}
