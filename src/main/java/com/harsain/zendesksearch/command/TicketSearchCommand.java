package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
import com.harsain.zendesksearch.exception.NoTicketFoundException;
import com.harsain.zendesksearch.service.TicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class TicketSearchCommand {

  @Autowired
  private TicketService ticketService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @ShellMethod(value = "Ticket field search", key = "ticket-search")
  public String search(
      @ShellOption(value = {"--key", "-K"}, help = "The key to match value for") String key,
      @ShellOption(defaultValue = "", help = "The value to match", value = {"--value",
          "-V"}) String value)
      throws JsonProcessingException {
    List<TicketResponseDto> ticketResponseDtos = ticketService.findBy(key, value.toString());
    if (!ticketResponseDtos.isEmpty()) {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketResponseDtos);
    } else {
      throw new NoTicketFoundException(
          String.format("No records found for [KEY: %s] & [VALUE: %s]", key, value));
    }
  }
}
