/**
 * 
 */
package ru.soultakov.remotecontrol.core.impl;

import java.util.List;
import java.util.TimerTask;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.IJobProvider;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalCommandException;
import ru.soultakov.remotecontrol.core.exceptions.JobProvidingException;

final class ExecuteCommandTask extends TimerTask {

    private final TimerCommandServiceImpl timerCommandService;

    private volatile boolean enabled = true;

    /**
     * @param timerCommandServiceImpl
     */
    ExecuteCommandTask(TimerCommandServiceImpl timerCommandServiceImpl) {
        this.timerCommandService = timerCommandServiceImpl;
    }

    @Override
    public void run() {
        if (isEnabled()) {
            final List<IJobProvider> jobProviders = this.timerCommandService.getJobProviders();
            for (final IJobProvider provider : jobProviders) {
                try {
                    for (final IJob job : provider.getJobs()) {
                        executeJob(job);
                    }
                } catch (final JobProvidingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void executeJob(final IJob job) {
        timerCommandService.getExecutorService().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = ExecuteCommandTask.this.timerCommandService
                            .getCommandExecutor().execute(job.getCommandText());
                    job.done(result, true);
                } catch (final IllegalCommandException e) {
                    job.done(e.getMessage(), false);
                } catch (final CommandExecutionException e) {
                    job.done(e.getMessage(), false);
                }
            }
        });
    }

    public void setEnabled(boolean paused) {
        this.enabled = paused;
    }

    public boolean isEnabled() {
        return enabled;
    }
}