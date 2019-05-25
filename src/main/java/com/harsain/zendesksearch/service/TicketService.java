package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
import com.harsain.zendesksearch.mapper.TicketMapper;
import com.harsain.zendesksearch.util.FilterPredicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

  @Autowired
  private OnLoad onLoad;
  @Autowired
  private OrganisationService organisationService;
  @Autowired
  private UserService userService;

  private FilterPredicate filterPredicate;

  {
    filterPredicate = new FilterPredicate();
  }

  public List<TicketResponseDto> findBy(String key, String value) {
    List<TicketResponseDto> ticketResponseDtoList = new ArrayList<>();
    List<Ticket> tickets = onLoad.getTickets();
    if (!tickets.isEmpty()) {
      List<Ticket> results = tickets.stream()
          .filter(filterPredicate.getGenericPredicate(key, value))
          .collect(Collectors.toList());
      results.forEach(ticket -> {
        Organisation organisation = organisationService.findById(ticket.getOrganization_id());
        User assigneeUser = userService.findById(ticket.getAssignee_id());
        User submitterUser = userService.findById(ticket.getSubmitter_id());
        ticketResponseDtoList.add(Mappers.getMapper(TicketMapper.class)
            .ticketToTicketResponseDto(ticket, submitterUser, assigneeUser, organisation));
      });
    }

    return ticketResponseDtoList;
  }
}
