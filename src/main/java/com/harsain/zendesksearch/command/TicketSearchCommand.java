package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
import com.harsain.zendesksearch.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class TicketSearchCommand implements CommandBase {
    @Autowired
    private TicketService ticketService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @ShellMethod(value = "Ticket field search", key = "ticket-search")
    public void search(@ShellOption({"--key", "-k"}) String key, @ShellOption({"--value", "-v"}) String value) {
        List<TicketResponseDto> ticketResponseDtos = ticketService.findBy(key, value.toString());
        if (ticketResponseDtos.size() > 0) {
            ticketResponseDtos.forEach(ticketResponseDto -> {
                try {
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketResponseDto));
                } catch (JsonProcessingException e) {
                    System.out.println("OOPS! unable to print the JSON string");
                }
            });
        } else {
            System.out.println(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
        }
    }
}
