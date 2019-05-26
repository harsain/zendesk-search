package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.response.UserResponseDto;
import com.harsain.zendesksearch.exception.NoUsersFoundException;
import com.harsain.zendesksearch.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserSearchCommand {

  @Autowired
  private UserService userService;

  private ObjectMapper objectMapper = new ObjectMapper();

  @ShellMethod(value = "User Search Field", key = "user-search")
  public String search(
      @ShellOption(value = {"--key", "-K"}, help = "The key to match value for") String key,
      @ShellOption(defaultValue = "", help = "The value to match", value = {"--value",
          "-V"}) Object val)
      throws JsonProcessingException {
    List<UserResponseDto> users = userService.findBy(key, val.toString());
    if (!users.isEmpty()) {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
    } else {
      throw new NoUsersFoundException(
          String.format("No records found for [KEY: %s] & [VALUE: %s]", key, val));
    }
  }
}
