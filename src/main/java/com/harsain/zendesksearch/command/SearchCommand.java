package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ShellComponent
public class SearchCommand {

    @Autowired
    private OnLoad onLoad;

    @ShellMethod("Search Field")
    public void search(@ShellOption("--key") String key, @ShellOption("--value") Object val) {
        List<User> users = onLoad.getUsers();
        System.out.println(String.format("Users found %d", users.size()));

        List<User> results = users.stream()
            .filter(new Predicate<User>() {
                @Override
                public boolean test(User user) {
                    try {
                        Field field = user.getClass().getDeclaredField(key);
                        field.setAccessible(true);
                        if (field.get(user) != null) {
                            if (field.get(user) instanceof ArrayList)
                                return ((ArrayList) field.get(user)).contains(val);
                            return field.get(user).toString().equalsIgnoreCase(val.toString());
                        }
                        return false;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        ).collect(Collectors.toList());
        if (results.size() > 0) {
            results.forEach(System.out::println);
        } else {
            System.out.println(String.format("No records found for [KEY: %s] & [VALUE: %s]", key, val));
        }
    }
}
