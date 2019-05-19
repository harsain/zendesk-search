package com.harsain.zendesksearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZendeskSearchApplication {

  public static void main(String[] args) {
    OnLoad onLoad = new OnLoad();
    try {
      onLoad.run(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
    SpringApplication.run(ZendeskSearchApplication.class, args);
  }

}

