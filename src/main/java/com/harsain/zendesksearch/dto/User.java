package com.harsain.zendesksearch.dto;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class User {
  private String _id;
  private String shared;
  private String last_login_at;
  private String role;
  private String signature;
  private String timezone;
  private String verified;
  private String created_at;
  private String active;
  private String external_id;
  private String locale;
  private String url;
  private String suspended;
  private List<String> tags;
  private String phone;
  private String organization_id;
  private String name;
  private String alias;
  private String email;

  public static List<String> getProperties() {
    return Arrays.stream(User.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
  }
}