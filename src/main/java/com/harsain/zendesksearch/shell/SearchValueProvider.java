package com.harsain.zendesksearch.shell;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;
import org.springframework.stereotype.Component;

@Component
public class SearchValueProvider implements ValueProvider {

  @Override
  public boolean supports(MethodParameter methodParameter, CompletionContext completionContext) {
    return true;
  }

  @Override
  public List<CompletionProposal> complete(MethodParameter methodParameter,
      CompletionContext completionContext, String[] strings) {
    List<CompletionProposal> result = new ArrayList<>();
    List<String> knownCommands = new ArrayList<>();
    knownCommands.add("list");
    knownCommands.add("search");
    knownCommands.add("user-search");
    knownCommands.add("organisation-search");
    knownCommands.add("ticket-search");

    String userInput = completionContext.currentWordUpToCursor();
    knownCommands.stream()
        .filter(cmd -> cmd.startsWith(userInput))
        .forEach(cmd -> result.add(new CompletionProposal(cmd)));

    return result;
  }
}
