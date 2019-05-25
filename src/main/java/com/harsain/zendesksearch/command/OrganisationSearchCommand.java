package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import com.harsain.zendesksearch.exception.NoOrganisationFoundException;
import com.harsain.zendesksearch.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

@ShellComponent
public class OrganisationSearchCommand {

    @Autowired
    private OrganisationService organisationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @ShellMethod(value = "Organisation field search", key = "organisation-search")
    public List<OrganisationResponseDto> search(String key, String value) throws NoOrganisationFoundException {
        List<OrganisationResponseDto> organisationResponseDtos = organisationService.findBy(key, value.toString());
        if (organisationResponseDtos.size() > 0) {
            organisationResponseDtos.forEach(x -> {
                try {
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(x));
                } catch (JsonProcessingException e) {
                    System.out.println("OOPS unable to print the JSON string");
                }
            });
            return organisationResponseDtos;
        } else {
            System.out.println(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
            throw new NoOrganisationFoundException(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
        }
    }
}
