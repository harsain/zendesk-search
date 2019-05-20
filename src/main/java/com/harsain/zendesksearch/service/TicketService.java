package com.harsain.zendesksearch.service;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.TicketResponseDto;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.mapper.TicketMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private OnLoad onLoad;
    @Autowired
    private OrganisationService organisationService;
    @Autowired
    private UserService userService;

    public List<TicketResponseDto> findBy(String key, String value) {
        List<TicketResponseDto> ticketResponseDtoList = new ArrayList<>();
        List<Ticket> tickets = onLoad.getTickets();
        if(tickets.size() > 0) {
            List<Ticket> results = tickets.stream()
                    .filter(new Predicate<Ticket>() {
                        @Override
                        public boolean test(Ticket ticket) {
                            try {
                                Method getter = new PropertyDescriptor(key, ticket.getClass()).getReadMethod();
                                if (getter != null) {
                                    Object valueRead = getter.invoke(ticket);
                                    if (getter.getReturnType().equals(List.class)) {
                                        return ((ArrayList) valueRead).stream().anyMatch(item -> item.toString().equalsIgnoreCase(value));
                                    }
                                    return valueRead.toString().equalsIgnoreCase(value);
                                }
                                return false;
                            } catch (Exception e) {
                                return false;
                            }
                        }
                    }).collect(Collectors.toList());
            results.stream().forEach(ticket -> {
                Organisation organisation = organisationService.findById(ticket.getOrganization_id());
                User assigneeUser = userService.findById(ticket.getAssignee_id());
                User submitterUser = userService.findById(ticket.getSubmitter_id());
                ticketResponseDtoList.add(Mappers.getMapper(TicketMapper.class).ticketToTicketRespnoseDto(ticket, submitterUser, assigneeUser, organisation));
            });
        }

        return ticketResponseDtoList;
    }
}
