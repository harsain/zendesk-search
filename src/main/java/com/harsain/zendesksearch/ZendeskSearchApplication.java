package com.harsain.zendesksearch;

import com.harsain.zendesksearch.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableConfigurationProperties
public class ZendeskSearchApplication {
  @Autowired
  private static ShellHelper shellHelper;

  public static void main(String[] args) {
    OnLoad onLoad = new OnLoad();
    try {
      onLoad.run(args);
      String[] disabledCommands = {"--spring.shell.command.stacktrace.enabled=false"};
      String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
      SpringApplication.run(ZendeskSearchApplication.class, fullArgs);
    } catch (Exception e) {
      shellHelper.printError(e.getMessage());
    }
  }

}

