/**
 * 
 */
package ru.soultakov.remotecontrol.core.impl.mail;

import java.io.IOException;
import java.util.TimerTask;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalJobException;

final class ReceiveMessagesTask extends TimerTask {

    private static final Logger LOGGER = Logger.getLogger(ReceiveMessagesTask.class);

    private final MailJobProvidingService mailJobProvidingService;
    private volatile boolean enabled = true;

    /**
     * @param mailJobProvidingService
     */
    ReceiveMessagesTask(MailJobProvidingService mailJobProvidingService) {
        this.mailJobProvidingService = mailJobProvidingService;
    }

    @Override
    public void run() {
        LOGGER.info("Waiting for new messages...");
        try {
            mailJobProvidingService.receiveAndExecute();
        } catch (final MessagingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (final IOException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (final IllegalJobException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (final CommandExecutionException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void setEnabled(boolean paused) {
        this.enabled = paused;
    }

    public boolean isEnabled() {
        return enabled;
    }

}