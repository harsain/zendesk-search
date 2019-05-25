package com.harsain.zendesksearch.dto.response;

import java.util.List;
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
  private List<String> domain_names;
  private List<String> tags;
}
