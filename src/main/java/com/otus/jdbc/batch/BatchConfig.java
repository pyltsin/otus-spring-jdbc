package com.otus.jdbc.batch;

import com.otus.jdbc.dao.JPAReader;
import com.otus.jdbc.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class BatchConfig {
    private final Logger logger = LoggerFactory.getLogger("Batch");

    @Autowired
    JPAReader jpaReader;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public JpaItemWriter<Author> writer()  {
        JpaItemWriter<Author> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(emf);
        try {
            writer.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Bean
    public MongoItemReader<Author> reader() {
        Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>(1);
        sorts.put("id", Sort.Direction.ASC);

        MongoItemReaderBuilder<Author> builder = new MongoItemReaderBuilder<>();
        builder
                .collection("author")
                .name("r1")
                .targetType(Author.class)
                .jsonQuery("{}")
                .saveState(true)
                .sorts(sorts)
                .template(mongoTemplate);
        return builder.build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Author, Author>chunk(10)
                .reader(reader())
                .writer(writer())
                .listener(new ItemReadListener() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(Object o) {
                        logger.info(o.toString());
                        logger.info("Конец чтения");
                    }

                    public void onReadError(Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener() {
                    public void beforeWrite(List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(Exception e, List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importProductsJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                        logger.info(jpaReader.findAll().toString());
                    }
                })
                .build();
    }

}
