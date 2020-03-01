package com.springbootbatchdemo.batchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.springbootbatchdemo.batchdemo.listener.JobListener;
import com.springbootbatchdemo.batchdemo.tasklet.SampleTasklet;

@Configuration
@EnableBatchProcessing
public class SampleJobConfig {
  @Autowired
  private SampleTasklet tasklet;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBulderFactory;

  @Bean
  public Step sampleStep() {
    return stepBulderFactory.get("sampleStep").tasklet(tasklet).build();
  }

  @Bean
  public Job job(Step step1) {
    return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener())
        .start(step1).build();
  }

  @Bean
  public JobExecutionListener listener() {
    return new JobListener();
  }
}
