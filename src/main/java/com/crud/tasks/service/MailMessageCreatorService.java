package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class MailMessageCreatorService {
    private static final String defaultAdminTemplate = "mail/created-trello-card-mail";

    private static final List<String> functionality = Collections.unmodifiableList(Arrays.asList(
            "You can manage your tasks",
            "Provides connection with Trello Account",
            "Application allows sending tasks to Trello"
    ));

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private CompanyData companyData;

    private Map<String, Object> defaultInitialAdminContextData;

    @PostConstruct
    public void initDefaultContextData() {
        defaultInitialAdminContextData = new HashMap<>();
        defaultInitialAdminContextData.put("taskUrl", "http://localhost");
        defaultInitialAdminContextData.put("button", "Visit website");
        defaultInitialAdminContextData.put("adminConfig", adminConfig);
        defaultInitialAdminContextData.put("companyData", companyData);
        defaultInitialAdminContextData.put("applicationFunctionality", functionality);
    }

    //
    public String buildMessage(MailMessageType messageType, String message) {
        switch(messageType) {
            case NEW_CARD_MESSAGE:
            case SCHEDULER_INFO_MESSAGE:
                return buildMessage(messageType, message, defaultAdminTemplate, defaultInitialAdminContextData);
            case SIMPLE_MESSAGE:
               return message;
            default:
                throw new IllegalStateException(String.format("Type %s is not supported", messageType));
        }
    }

    private String buildMessage(MailMessageType messageType, String message, String template,
                              Map<String, Object> initialContextData) {
        Context context = new Context();
        context.setVariables(initialContextData);
        context.setVariable("msgType", messageType);
        context.setVariable("message", message);
        return templateEngine.process(template, context);
    }
}
