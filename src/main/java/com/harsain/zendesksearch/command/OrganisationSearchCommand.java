package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.OrganisationResponseDto;
import com.harsain.zendesksearch.dto.UserResponseDto;
import com.harsain.zendesksearch.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@ShellComponent
public class OrganisationSearchCommand implements CommandBase {

    @Autowired
    private OrganisationService organisationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @ShellMethod(value = "Organisation field search", key = "organisation-search")
    public void search(String key, String value) {
        List<OrganisationResponseDto> organisationResponseDtos = organisationService.findBy(key, value.toString());
        if (organisationResponseDtos.size() > 0) {
            organisationResponseDtos.forEach(x -> {
                try {
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(x));
                } catch (JsonProcessingException e) {
                    System.out.println("OOPS unable to print the JSON string");
                }
            });
        } else {
            System.out.println(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
        }
    }
}
