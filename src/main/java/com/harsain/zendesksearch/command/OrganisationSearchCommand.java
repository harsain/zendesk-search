package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import com.harsain.zendesksearch.exception.NoOrganisationFoundException;
import com.harsain.zendesksearch.service.OrganisationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class OrganisationSearchCommand {

  @Autowired
  private OrganisationService organisationService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @ShellMethod(value = "Organisation field search", key = "organisation-search")
  public String search(String key, String value)
      throws NoOrganisationFoundException, JsonProcessingException {
    List<OrganisationResponseDto> organisationResponseDtos = organisationService
        .findBy(key, value.toString());
    if (organisationResponseDtos.size() > 0) {
      return objectMapper.writerWithDefaultPrettyPrinter()
          .writeValueAsString(organisationResponseDtos);
    } else {
      throw new NoOrganisationFoundException(
          String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
    }
  }
}
