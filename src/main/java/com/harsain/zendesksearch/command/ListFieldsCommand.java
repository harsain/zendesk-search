package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.exception.InvalidInputException;
import com.harsain.zendesksearch.util.ModelUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent()
public class ListFieldsCommand {

  @Autowired
  private ModelUtils modelUtils;

  @ShellMethod("Lists all the possible fields available to search on")
  public List<String> list() throws InvalidInputException {

    List<String> result = new ArrayList<>();
    result.add(
        "---------------------------------------- USER -------------------------------------------------");
    result.addAll(modelUtils.getPropertiesForClass(User.class));
    result.add(
        "---------------------------------------- ORGANISATION -------------------------------------------------");
    result.addAll(modelUtils.getPropertiesForClass(Organisation.class));
    result.add(
        "---------------------------------------- TICKETS -------------------------------------------------");
    result.addAll(modelUtils.getPropertiesForClass(Ticket.class));

    return result;
  }
}
