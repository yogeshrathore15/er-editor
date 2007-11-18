package ru.soultakov.remotecontrol.core.impl.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.IJobProvider;
import ru.soultakov.remotecontrol.core.exceptions.JobProvidingException;
import ru.soultakov.remotecontrol.mail.IMessageProcessor;
import ru.soultakov.remotecontrol.mail.MailService;

public class MailJobProvider implements IJobProvider {

    private static final Logger LOGGER = Logger.getLogger(MailJobProvider.class);

    private MailService mailService;
    private String subjectPattern;

    private final IMessageProcessor<MailJob> messageProcessor = new IMessageProcessor<MailJob>() {
        @Override
        public List<MailJob> processMessages(List<Message> messages) throws MessagingException,
                IOException {
            LOGGER.info(messages.size() + " messages to process");
            final ArrayList<MailJob> jobs = new ArrayList<MailJob>();
            for (final Message m : messages) {
                String subject = m.getSubject();
                LOGGER.info("Processing message with subject '" + subject + "'");
                if (subject.matches(subjectPattern)) {
                    LOGGER.info("Adding new job...");
                    jobs.add(new MailJob(mailService, m));
                }
            }
            return jobs;
        }
    };;

    public String getSubjectPattern() {
        return this.subjectPattern;
    }

    public void setSubjectPattern(String subjectPattern) {
        this.subjectPattern = subjectPattern;
    }

    public MailService getMailService() {
        return this.mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public List<? extends IJob> getJobs() throws JobProvidingException {
        try {
            return mailService.receiveUnreadMessages(messageProcessor);
        } catch (final MessagingException e) {
            throw new JobProvidingException(e.getMessage(), e);
        } catch (final IOException e) {
            throw new JobProvidingException(e.getMessage(), e);
        }
    }
}
