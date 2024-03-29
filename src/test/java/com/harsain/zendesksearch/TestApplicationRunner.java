package com.harsain.zendesksearch;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestApplicationRunner implements ApplicationRunner {

  private static Logger log = LoggerFactory.getLogger(TestApplicationRunner.class);

  public TestApplicationRunner() {
    log.info("CommandTest Application Runner started!");
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("About to do nothing!");
    // Do nothing...
  }

}
