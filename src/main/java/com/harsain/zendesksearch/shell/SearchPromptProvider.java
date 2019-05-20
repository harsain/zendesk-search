package com.harsain.zendesksearch.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SearchPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString( "Zendesk-search:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }
}
