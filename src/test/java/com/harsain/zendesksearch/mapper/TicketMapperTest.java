package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.response.OrganisationResponseDto;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TicketMapperTest {
    @Test
    public void testTicketMapping() {
        Organisation organisation = new Organisation();
        organisation.set_id("101");
        Ticket ticket = new Ticket();
        ticket.set_id("1001");
        ticket.setAssignee_id("1");
        ticket.setSubmitter_id("2");
        User assignee = new User();
        assignee.set_id("1");
        User submitter = new User();
        submitter.set_id("2");

        TicketResponseDto ticketResponseDto = Mappers.getMapper(TicketMapper.class).ticketToTicketResponseDto(ticket, submitter, assignee, organisation);

        Assertions.assertNotNull(ticketResponseDto);
        Assertions.assertEquals(User.class, ticketResponseDto.getSubmitterUser().getClass());
        Assertions.assertEquals(User.class, ticketResponseDto.getAssigneeUser().getClass());
        Assertions.assertEquals(Organisation.class, ticketResponseDto.getOrganization().getClass());
        Assertions.assertEquals("1001", ticketResponseDto.get_id());
        Assertions.assertEquals("2", ticketResponseDto.getSubmitterUser().get_id());
        Assertions.assertEquals("1", ticketResponseDto.getAssigneeUser().get_id());
        Assertions.assertEquals("101", ticketResponseDto.getOrganization().get_id());
    }
}
