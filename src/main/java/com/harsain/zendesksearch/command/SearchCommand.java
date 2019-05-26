package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harsain.zendesksearch.service.SearchService;
import com.harsain.zendesksearch.shell.InputReader;
import com.harsain.zendesksearch.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class SearchCommand {

  @Autowired
  private ShellHelper shellHelper;
  @Autowired
  private InputReader inputReader;
  @Autowired
  private SearchService searchService;

  @ShellMethod(value = "Search", key = "search")
  public String search() throws JsonProcessingException {

    String entity;
    String key;
    String value;
    shellHelper.printInfo("Please enter new user data:");
    // 1. read entity --------------------------------------------
    do {
      entity = inputReader.prompt("Entity [user, organisation, ticket]");
      if (!StringUtils.hasText(entity)) {
        shellHelper.printWarning("Entity CAN NOT be empty string? Please enter valid value!");
      }
    } while (entity == null);

    // 2. read entity's attribute --------------------------------------------
    do {
      key = inputReader.prompt(String.format("Entity: %s KEY", entity), null, false);
      if (!StringUtils.hasText(key)) {
        shellHelper.printWarning("KEY CAN NOT be empty string? Please enter valid value!");
      }
    } while (key == null);

    // 3. read value to match
    do {
      value = inputReader
          .prompt(String.format("ENTITY: %s KEY: %s, VALUE", entity, key), "", false);
      if (!StringUtils.hasText(key)) {
        shellHelper.printWarning("KEY CAN NOT be empty string? Please enter valid value!");
      }
    } while (value == null);

    return searchService.search(entity, key, value);
  }
}
