package com.harsain.zendesksearch;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class SearchPromptProvider implements PromptProvider {

  @Override
  public AttributedString getPrompt() {
    return new AttributedString("zendesk-search:>", AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
  }
}
