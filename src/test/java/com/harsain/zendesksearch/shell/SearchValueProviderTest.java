package com.harsain.zendesksearch.shell;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.shell.*;
import org.springframework.shell.standard.CommandValueProvider;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

public class SearchValueProviderTest {

    @Mock
    private Shell shell;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testValues() {
        SearchValueProvider searchValueProvider = new SearchValueProvider();

        Method help = ReflectionUtils.findMethod(Command.class, "help", String.class);
        MethodParameter methodParameter = Utils.createMethodParameter(help, 0);
        CompletionContext completionContext = new CompletionContext(Arrays.asList("help", "m"), 0, 0);
        boolean supports = searchValueProvider.supports(methodParameter, completionContext);

        Assertions.assertTrue(supports);

        List<CompletionProposal> proposals = searchValueProvider.complete(methodParameter, completionContext, new String[0]);

        Assertions.assertTrue(proposals.stream().map(proposal -> proposal.value()).collect(Collectors.toList()).contains("list"));

    }

    public static class Command {

        public void help(@ShellOption(valueProvider = CommandValueProvider.class) String command) {}
    }
}
