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

    // TODO : add new exception type to protect against crashing job provider
    // when one mail job can't be created
    public MailJob(MailService mailService, Message message) throws MessagingException, IOException {
        this.mailService = mailService;
        // TODO : replace with a polymorphism
        if (message.getContentType().startsWith("text/plain")) {
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
