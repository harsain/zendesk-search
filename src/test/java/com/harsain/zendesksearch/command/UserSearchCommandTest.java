package com.harsain.zendesksearch.command;

import com.harsain.zendesksearch.OnLoad;
import com.harsain.zendesksearch.exception.NoUsersFoundException;
import com.harsain.zendesksearch.service.OrganisationService;
import com.harsain.zendesksearch.service.UserService;
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

@ExtendWith(MockitoExtension.class)
public class UserSearchCommandTest {
    private static StandardMethodTargetRegistrar registrar = new StandardMethodTargetRegistrar();
    private static ConfigurableCommandRegistry registry = new ConfigurableCommandRegistry();

    @InjectMocks
    private static UserSearchCommand userSearchCommand;

    @BeforeAll
    public static void setUp() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OnLoad.class);
        context.register(UserService.class);
        context.register(OrganisationService.class);
        context.register(UserSearchCommand.class);
        registrar.setApplicationContext(context);
        registrar.register(registry);
    }

    @Test
    public void userSearchCommandTest() {
        String excepted = "[UserResponseDto(_id=1, shared=false, last_login_at=2013-08-04T01:03:27 -10:00, role=admin, signature=Don't Worry Be Happy!, timezone=Sri Lanka, verified=true, created_at=2016-04-15T05:19:46 -10:00, active=true, external_id=74341f74-9c79-49d5-9611-87ef9b6eb75f, locale=en-AU, url=http://initech.zendesk.com/api/v2/users/1.json, suspended=true, tags=[Springville, Sutton, Hartsville/Hartley, Diaperville], phone=8335-422-718, organisationObj=Organisation(_id=119, shared_tickets=false, name=Multron, created_at=2016-02-29T03:45:12 -11:00, external_id=2386db7c-5056-49c9-8dc4-46775e464cb7, details=Non profit, url=http://initech.zendesk.com/api/v2/organizations/119.json, domain_names=[bleeko.com, pulze.com, xoggle.com, sultraxin.com], tags=[Erickson, Mccoy, Wiggins, Brooks]), name=Francisca Rasmussen, alias=Miss Coffey, email=coffeyrasmussen@flotonic.com)]";
        Map<String, MethodTarget> commands = registry.listCommands();
        MethodTarget methodTarget = commands.get("user-search");

        Assertions.assertNotNull(methodTarget);
        Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils.findMethod(UserSearchCommand.class, "search", String.class, Object.class));
        Assertions.assertTrue(methodTarget.getAvailability().isAvailable());
        Assertions.assertEquals(excepted, ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "_id", "1").toString());
    }

    @Test
    public void userSearchCommandInvalidFieldTest() {
        Map<String, MethodTarget> commands = registry.listCommands();
        MethodTarget methodTarget = commands.get("user-search");

        Assertions.assertNotNull(methodTarget);
        Assertions.assertEquals(methodTarget.getMethod(), ReflectionUtils.findMethod(UserSearchCommand.class, "search", String.class, Object.class));
        Assertions.assertTrue(methodTarget.getAvailability().isAvailable());

        Exception exception = Assertions.assertThrows(NoUsersFoundException.class, () -> ReflectionUtils.invokeMethod(methodTarget.getMethod(), methodTarget.getBean(), "id", "1"));
        Assertions.assertEquals("No records found for [KEY: id] & [VALUE: 1]", exception.getMessage());
    }
}
