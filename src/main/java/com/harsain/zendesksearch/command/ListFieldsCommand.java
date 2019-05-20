package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.dto.Organisation;
import com.harsain.zendesksearch.dto.Ticket;
import com.harsain.zendesksearch.dto.User;
import com.harsain.zendesksearch.shell.InputReader;
import com.harsain.zendesksearch.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

@ShellComponent()
public class ListFieldsCommand {

    @ShellMethod("Lists all the possible fields available to search on")
    public void list() {

        List<String> userFieldsList = User.getProperties();
        List<String> organisationFieldsList = Arrays.stream(Organisation.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
        List<String> ticketFieldsList = Arrays.stream(Ticket.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());

        out.println("-----------------------------------------------------------------------------------------");

        out.println(String.format("Fields under %s available for search", User.class.getSimpleName()));
        userFieldsList.forEach( field -> out.println(field));

        out.println("-----------------------------------------------------------------------------------------");

        out.println(String.format("Fields under %s available for search", Organisation.class.getSimpleName()));
        organisationFieldsList.forEach( field -> out.println(field));

        out.println("-----------------------------------------------------------------------------------------");

        out.println(String.format("Fields under %s available for search", Ticket.class.getSimpleName()));
        ticketFieldsList.forEach( field -> out.println(field));

        out.println("-----------------------------------------------------------------------------------------");
    }
}
