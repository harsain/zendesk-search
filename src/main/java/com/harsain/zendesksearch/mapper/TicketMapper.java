package com.harsain.zendesksearch.mapper;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.response.TicketResponseDto;
import com.harsain.zendesksearch.dto.User;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class TicketMapper {
    public TicketResponseDto ticketToTicketRespnoseDto(Ticket ticket, User submitterUser, User assigneeUser, Organisation organisation) {
        if ( ticket == null ) {
            return null;
        }

        TicketResponseDto ticketResponseDto = new TicketResponseDto();

        ticketResponseDto.set_id( ticket.get_id() );
        ticketResponseDto.setSubject( ticket.getSubject() );
        ticketResponseDto.setCreated_at( ticket.getCreated_at() );
        ticketResponseDto.setDescription( ticket.getDescription() );
        ticketResponseDto.setExternal_id( ticket.getExternal_id() );
        ticketResponseDto.setType( ticket.getType() );
        ticketResponseDto.setPriority( ticket.getPriority() );
        ticketResponseDto.setUrl( ticket.getUrl() );
        List<String> list = ticket.getTags();
        if ( list != null ) {
            ticketResponseDto.setTags( new ArrayList<String>( list ) );
        }
        ticketResponseDto.setVia( ticket.getVia() );
        ticketResponseDto.setDue_at( ticket.getDue_at() );
        ticketResponseDto.setHas_incidents( ticket.getHas_incidents() );
        ticketResponseDto.setStatus( ticket.getStatus() );

        ticketResponseDto.setSubmitterUser(submitterUser);
        ticketResponseDto.setAssigneeUser(assigneeUser);
        ticketResponseDto.setOrganization(organisation);

        return ticketResponseDto;
    }
}
