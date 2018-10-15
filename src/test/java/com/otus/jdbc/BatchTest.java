package com.otus.jdbc;


import com.otus.jdbc.dao.JPAReader;
import com.otus.jdbc.model.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

//@EnableBatchProcessing
@SpringBootTest(classes = JdbcApplication.class)
@TestPropertySource(properties = {"spring.batch.job.enabled = false"})
//@ContextConfiguration(classes = BatchConfig.class)
@RunWith(SpringRunner.class)
public class BatchTest {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JPAReader jpaReader;

    @Test
    public void launchJob() throws Exception {
        JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJob(job);
        //testing a job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
        List<Author> all = (List<Author>) jpaReader.findAll();
        assertEquals(all.size(), 2);

    }
}