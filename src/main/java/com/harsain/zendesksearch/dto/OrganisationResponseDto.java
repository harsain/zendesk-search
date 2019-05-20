package com.harsain.zendesksearch.dto;

import lombok.Data;

@Data
public class OrganisationResponseDto {
    private String _id;
    private String shared_tickets;
    private String name;
    private String created_at;
    private String external_id;
    private String details;
    private String url;
    private String[] domain_names;
    private String[] tags;
}
