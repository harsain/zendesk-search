package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.model.Organisation;
import com.harsain.zendesksearch.model.Ticket;
import com.harsain.zendesksearch.model.User;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent()
public class ListFieldsCommand {

    @ShellMethod("Lists all the possible fields available to search on")
    public void list() {
        List<String> userFieldsList = Arrays.stream(User.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> organisationFieldsList = Arrays.stream(Organisation.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> ticketFieldsList = Arrays.stream(Ticket.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println(String.format("Fields under %s available for search", User.class.getSimpleName()));
        userFieldsList.forEach( field -> System.out.println(field));

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println(String.format("Fields under %s available for search", Organisation.class.getSimpleName()));
        organisationFieldsList.forEach( field -> System.out.println(field));

        System.out.println("-----------------------------------------------------------------------------------------");

        System.out.println(String.format("Fields under %s available for search", Ticket.class.getSimpleName()));
        ticketFieldsList.forEach( field -> System.out.println(field));

        System.out.println("-----------------------------------------------------------------------------------------");
    }
}
