package com.harsain.zendesksearch.command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.dto.UserResponseDto;
import com.harsain.zendesksearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class UserSearchCommand {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @ShellMethod(value = "User Search Field", key = "user-search")
    public void search(@ShellOption({"--key", "-K"}) String key, @ShellOption({"--value", "-V"}) Object val) {
        List<UserResponseDto> users = userService.findBy(key, val.toString());
        if (users.size() > 0) {
            users.forEach(x -> {
                try {
                    System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(x));
                } catch (JsonProcessingException e) {
                    System.out.println("OOPS unable to print the JSON string");
                }
            });
        } else {
            System.out.println(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, val));
        }
    }
}
