package com.harsain.zendesksearch.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketResponseDto {
    private String _id;
    private String subject;
    private String created_at;
    private String description;
    private User submitterUser;
    private String external_id;
    private String type;
    private String priority;
    private String url;
    private List<String> tags;
    private String via;
    private Organisation organization;
    private String due_at;
    private String has_incidents;
    private String status;
    private User assigneeUser;
}
