package ru.soultakov.remotecontrol.core.impl.mail;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.mail.MailService;

public class MailJob implements IJob {

    private static final Logger LOGGER = Logger.getLogger(MailJob.class);

    private final MailService mailService;
    private String commandText;

    public MailJob(MailService mailService, Message message) throws MessagingException, IOException {
        this.mailService = mailService;
        // TODO : replace with a polymorphism
        if ("text/plain".equals(message.getContentType())) {
            commandText = (String) message.getContent();
            LOGGER.info("MailJob created. Command text = '" + commandText + "'");
        } else {
            LOGGER.error("Unsupported content type " + message.getContentType());
            throw new MessagingException("Unsupported content type " + message.getContentType());
        }
    }

    @Override
    public void done(String result, boolean success) {
        LOGGER.info((success ? "SUCCESS" : "FAIL") + ": " + result);
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}
