package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.exception.NoOrganisationFoundException;
import com.harsain.zendesksearch.service.OrganisationService;
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

import java.util.Map;

@ExtendWith({MockitoExtension.class})
public class OrganisationSearchCommandTest {
    private static StandardMethodTargetRegistrar registrar = new StandardMethodTargetRegistrar();
    private static ConfigurableCommandRegistry registry = new ConfigurableCommandRegistry();

    @InjectMocks
    private static OrganisationSearchCommand organisationSearchCommand;

    @BeforeAll
    public static void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OnLoad.class);
        context.register(OrganisationService.class);
        context.register(OrganisationSearchCommand.class);
        registrar.setApplicationContext(context);
        registrar.register(registry);
    }

    @Test
    public void userSearchCommandTest() {
        String expected = "[OrganisationResponseDto(_id=101, shared_tickets=false, name=Enthaze, created_at=2016-05-21T11:10:28 -10:00, external_id=9270ed79-35eb-4a38-a46f-35725197ea8d, details=MegaCorp, url=http://initech.zendesk.com/api/v2/organizations/101.json, domain_names=[kage.com, ecratic.com, endipin.com, zentix.com], tags=[Fulton, West, Rodriguez, Farley])]";
        Map<String, MethodTarget> commands = registry.listCommands();
        MethodTarget methodTarget = commands.get("organisation-search");

        Assertions.assertNotNull(methodTarget);
        Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils.findMethod(OrganisationSearchCommand.class, "search", String.class, String.class));
        Assertions.assertTrue(methodTarget.getAvailability().isAvailable());
        Assertions.assertEquals(expected, ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "_id", "101").toString());
    }

    @Test
    public void userSearchCommandInValidTest() {
        String expected = "No records found for [KEY: _id] & [VALUE: 100]";
        Map<String, MethodTarget> commands = registry.listCommands();
        MethodTarget methodTarget = commands.get("organisation-search");

        Assertions.assertNotNull(methodTarget);
        Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils.findMethod(OrganisationSearchCommand.class, "search", String.class, String.class));
        Assertions.assertTrue(methodTarget.getAvailability().isAvailable());

        Exception exception = Assertions.assertThrows(NoOrganisationFoundException.class, () -> ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "id", "101"));
        Assertions.assertEquals("No records found for [KEY: id] & [VALUE: 101]", exception.getMessage());
    }
}
