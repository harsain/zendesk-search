package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.exception.NoTicketFoundException;
import com.harsain.zendesksearch.service.OrganisationService;
import com.harsain.zendesksearch.service.TicketService;
import com.harsain.zendesksearch.service.UserService;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.ConfigurableCommandRegistry;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.standard.StandardMethodTargetRegistrar;
import org.springframework.util.ReflectionUtils;

@ExtendWith({MockitoExtension.class})
public class TicketSearchCommandTest {

  private static StandardMethodTargetRegistrar registrar = new StandardMethodTargetRegistrar();
  private static ConfigurableCommandRegistry registry = new ConfigurableCommandRegistry();

  @InjectMocks
  private static TicketSearchCommand ticketSearchCommand;

  @BeforeAll
  public static void setUp() {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        OnLoad.class);
    context.register(OrganisationService.class);
    context.register(UserService.class);
    context.register(TicketService.class);
    context.register(TicketSearchCommand.class);
    registrar.setApplicationContext(context);
    registrar.register(registry);
  }

  @Test
  public void ticketSearchCommandTest() {
    String expected = "[ {\n" +
        "  \"_id\" : \"436bf9b0-1147-4c0a-8439-6f79833bff5b\",\n" +
        "  \"subject\" : \"A Catastrophe in Korea (North)\",\n" +
        "  \"created_at\" : \"2016-04-28T11:19:34 -10:00\",\n" +
        "  \"description\" : \"Nostrud ad sit velit cupidatat laboris ipsum nisi amet laboris ex exercitation amet et proident. Ipsum fugiat aute dolore tempor nostrud velit ipsum.\",\n"
        +
        "  \"submitterUser\" : null,\n" +
        "  \"external_id\" : \"9210cdc9-4bee-485f-a078-35396cd74063\",\n" +
        "  \"type\" : \"incident\",\n" +
        "  \"priority\" : \"high\",\n" +
        "  \"url\" : \"http://initech.zendesk.com/api/v2/tickets/436bf9b0-1147-4c0a-8439-6f79833bff5b.json\",\n"
        +
        "  \"tags\" : [ \"Ohio\", \"Pennsylvania\", \"American Samoa\", \"Northern Mariana Islands\" ],\n"
        +
        "  \"via\" : \"web\",\n" +
        "  \"organization\" : {\n" +
        "    \"_id\" : \"116\",\n" +
        "    \"shared_tickets\" : \"false\",\n" +
        "    \"name\" : \"Zentry\",\n" +
        "    \"created_at\" : \"2016-01-13T09:34:07 -11:00\",\n" +
        "    \"external_id\" : \"dbc692fc-e1ae-47d8-a1d7-263d07710fe1\",\n" +
        "    \"details\" : \"Artisan\",\n" +
        "    \"url\" : \"http://initech.zendesk.com/api/v2/organizations/116.json\",\n" +
        "    \"domain_names\" : [ \"datagene.com\", \"exoteric.com\", \"beadzza.com\", \"digiprint.com\" ],\n"
        +
        "    \"tags\" : [ \"Schneider\", \"Hoover\", \"Wilcox\", \"Hewitt\" ]\n" +
        "  },\n" +
        "  \"due_at\" : \"2016-07-31T02:37:50 -10:00\",\n" +
        "  \"has_incidents\" : \"false\",\n" +
        "  \"status\" : \"pending\",\n" +
        "  \"assigneeUser\" : null\n" +
        "} ]";
    Map<String, MethodTarget> commands = registry.listCommands();
    MethodTarget methodTarget = commands.get("ticket-search");

    Assertions.assertNotNull(methodTarget);
    Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils
        .findMethod(TicketSearchCommand.class, "search", String.class, String.class));
    Assertions.assertTrue(methodTarget.getAvailability().isAvailable());
    Assertions.assertEquals(expected, ReflectionUtils
        .invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "_id",
            "436bf9b0-1147-4c0a-8439-6f79833bff5b").toString());
  }

  @Test
  public void ticketSearchCommandInArrayTest() {
    Map<String, MethodTarget> commands = registry.listCommands();
    MethodTarget methodTarget = commands.get("ticket-search");

    Assertions.assertNotNull(methodTarget);
    Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils
        .findMethod(TicketSearchCommand.class, "search", String.class, String.class));
    Assertions.assertTrue(methodTarget.getAvailability().isAvailable());
    String responseDtoList = ReflectionUtils
        .invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "tags", "utah").toString();
    Assertions.assertNotNull(responseDtoList);
    Assertions.assertTrue(responseDtoList.contains("Utah"));
  }

  @Test
  public void ticketSearchCommandInvalidTest() {
    Map<String, MethodTarget> commands = registry.listCommands();
    MethodTarget methodTarget = commands.get("ticket-search");

    Assertions.assertNotNull(methodTarget);
    Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils
        .findMethod(TicketSearchCommand.class, "search", String.class, String.class));
    Assertions.assertTrue(methodTarget.getAvailability().isAvailable());

    Exception exception = Assertions.assertThrows(NoTicketFoundException.class,
        () -> ReflectionUtils
            .invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "_id", "101"));
    Assertions
        .assertEquals("No records found for [KEY: _id] & [VALUE: 101]", exception.getMessage());
  }
}
