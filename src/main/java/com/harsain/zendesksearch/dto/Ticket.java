package com.harsain.zendesksearch.dto;

import lombok.Data;

import java.util.List;

@Data
public class Ticket {
  private String _id;
  private String subject;
  private String created_at;
  private String description;
  private String submitter_id;
  private String external_id;
  private String type;
  private String priority;
  private String url;
  private List<String> tags;
  private String via;
  private String organization_id;
  private String due_at;
  private String has_incidents;
  private String status;
  private String assignee_id;
}
