package com.harsain.zendesksearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

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
  private String[] tags;
  private String via;
  private String organization_id;
  private String due_at;
  private String has_incidents;
  private String status;
  private String assignee_id;
}
