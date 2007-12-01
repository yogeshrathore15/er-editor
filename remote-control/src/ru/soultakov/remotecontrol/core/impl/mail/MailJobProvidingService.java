package ru.soultakov.remotecontrol.core.impl.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.mail.Message;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.IJobExecutionService;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;
import ru.soultakov.remotecontrol.core.impl.AbstractJobProvidingService;
import ru.soultakov.remotecontrol.mail.IMessageProcessor;
import ru.soultakov.remotecontrol.mail.MailService;

public class MailJobProvidingService extends AbstractJobProvidingService {

    private static final Logger LOGGER = Logger.getLogger(MailJobProvidingService.class);

    private MailService mailService;
    private String subjectPattern;
    private Long period;
    private IJobExecutionService jobExecutionService;

    private Timer timer;
    private final CheckForNewMessagesTask timerTask = new CheckForNewMessagesTask(this);

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

    void receiveAndExecute() throws MessagingException, IOException, IllegalCommandException,
            CommandExecutionException {
        final List<MailJob> newJobs = mailService.receiveUnreadMessages(messageProcessor);
        for (final IJob job : newJobs) {
            jobExecutionService.execute(job);
        }
    }

    @Override
    protected void doResume() {
        timerTask.setEnabled(true);
    }

    @Override
    protected void doStart() {
        timer = new Timer();
        timer.schedule(timerTask, 0, period);
    }

    @Override
    protected void doStop() {
        timer.cancel();
    }

    @Override
    protected void doSuspend() {
        timerTask.setEnabled(false);
    }

    public Long getPeriod() {
        return this.period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public IJobExecutionService getJobExecutionService() {
        return this.jobExecutionService;
    }

    public void setJobExecutionService(IJobExecutionService jobExecutionService) {
        this.jobExecutionService = jobExecutionService;
    }

    public IMessageProcessor<MailJob> getMessageProcessor() {
        return this.messageProcessor;
    }

}
