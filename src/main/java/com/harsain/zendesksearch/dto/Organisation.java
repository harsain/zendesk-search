package com.harsain.zendesksearch.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organisation {
  private String _id;
  private String shared_tickets;
  private String name;
  private String created_at;
  private String external_id;
  private String details;
  private String url;
  @Nullable
  private List<String> domain_names;
  private List<String> tags;
}
