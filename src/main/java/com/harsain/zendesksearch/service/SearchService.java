package com.harsain.zendesksearch.service;

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

    public void search(String entity, String key, String value) {
        if(entity.equalsIgnoreCase("user")) {
            userSearchCommand.search(key, value);
        } else if( entity.equalsIgnoreCase("organisation")) {
            organisationSearchCommand.search(key, value);
        } else if (entity.contains("ticket")) {
            ticketSearchCommand.search(key, value);
        }
    }
}
