package ru.soultakov.remotecontrol.core.impl.mail;

import java.io.IOException;
import java.util.Arrays;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.mail.MailService;

public class MailJob implements IJob {

    private static final Logger LOGGER = Logger.getLogger(MailJob.class);

    private final MailService mailService;
    private final String commandText;
    private final Address[] customerAddresses;

    // TODO : add new exception type to protect against crashing job provider
    // when one mail job can't be created
    public MailJob(MailService mailService, Message message) throws MessagingException, IOException {
        this.mailService = mailService;
        customerAddresses = message.getFrom().clone();
        if ((customerAddresses == null) || (customerAddresses.length == 0)) {
            throw new IllegalArgumentException("Illegal 'from' addresses : "
                    + Arrays.toString(customerAddresses));
        }
        // TODO : replace with a polymorphism
        if (message.getContentType().startsWith("text/plain")) {
            commandText = ((String) message.getContent());
            LOGGER.info("MailJob created. Command text = '" + commandText + "'");
        } else {
            LOGGER.error("Unsupported content type " + message.getContentType());
            throw new MessagingException("Unsupported content type " + message.getContentType());
        }
    }

    @Override
    public void done(String result, boolean success) {
        final String stringSuccess = (success ? "SUCCESS" : "FAIL");
        LOGGER.info(stringSuccess + ": " + result);
        for (final Address address : customerAddresses) {
            try {
                mailService.sendMessage(address, "Re: COMMAND. Result : " + stringSuccess, result);
                LOGGER.info("Successfully replied to address '" + address + "'");
            } catch (final MessagingException e) {
                LOGGER.error("Cannot reply the command. Address = " + address, e);
            }
        }
    }

    @Override
    public String getCommandText() {
        return commandText;
    }

}
