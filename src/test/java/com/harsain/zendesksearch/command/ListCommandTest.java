package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.util.ModelUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.ConfigurableCommandRegistry;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.standard.StandardMethodTargetRegistrar;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.util.Map;

public class ListCommandTest {

    private static StandardMethodTargetRegistrar registrar =
            new StandardMethodTargetRegistrar();
    private static ConfigurableCommandRegistry registry =
            new ConfigurableCommandRegistry();

    @BeforeAll
    public static void setUp() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ModelUtils.class);
        context.register(ListFieldsCommand.class);
        registrar.setApplicationContext(context);
        registrar.register(registry);
    }

    @Test
    public void listFieldsCommandTest() {
        String expectedString = "[---------------------------------------- USER -------------------------------------------------, _id, shared, last_login_at, role, signature, timezone, verified, created_at, active, external_id, locale, url, suspended, tags, phone, organization_id, name, alias, email, ---------------------------------------- ORGANISATION -------------------------------------------------, _id, shared_tickets, name, created_at, external_id, details, url, domain_names, tags, ---------------------------------------- TICKETS -------------------------------------------------, _id, subject, created_at, description, submitter_id, external_id, type, priority, url, tags, via, organization_id, due_at, has_incidents, status, assignee_id]";
        Map<String, MethodTarget> commands = registry.listCommands();

        MethodTarget methodTarget = commands.get("list");
        Assertions.assertNotNull(methodTarget);

        Assertions.assertEquals(methodTarget.getGroup(), "List Fields Command");
        Assertions.assertEquals(methodTarget.getHelp(), "Lists all the possible fields available to search on");
        Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils.findMethod(ListFieldsCommand.class, "list"));
        Assertions.assertTrue(methodTarget.getAvailability().isAvailable());
        Assertions.assertEquals(expectedString.trim(), ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean()).toString());
    }
}
