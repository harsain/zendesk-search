package com.harsain.zendesksearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.harsain.zendesksearch.command.OrganisationSearchCommand;
import com.harsain.zendesksearch.command.TicketSearchCommand;
import com.harsain.zendesksearch.command.UserSearchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

  @Autowired
  private UserSearchCommand userSearchCommand;
  @Autowired
  private OrganisationSearchCommand organisationSearchCommand;
  @Autowired
  private TicketSearchCommand ticketSearchCommand;

  public String search(String entity, String key, String value) throws JsonProcessingException {
    if (entity.equalsIgnoreCase("user")) {
      return userSearchCommand.search(key, value);
    } else if (entity.equalsIgnoreCase("organisation")) {
      return organisationSearchCommand.search(key, value);
    } else if (entity.contains("ticket")) {
      return ticketSearchCommand.search(key, value);
    } else {
      throw new RuntimeException("Invalid entity option");
    }
  }
}
