package ru.soultakov.remotecontrol;

import java.util.List;

import org.apache.log4j.Logger;

import ru.soultakov.remotecontrol.core.IJobExecutionService;
import ru.soultakov.remotecontrol.core.IJobProvidingService;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    private static Application instance;

    private List<IJobExecutionService> jobExecutionServices;
    private List<IJobProvidingService> jobProvidingServices;

    private Application() {
    }

    public void start() {
        LOGGER.info("Starting application...");
        for (final IJobExecutionService jobExecutionService : jobExecutionServices) {
            jobExecutionService.start();
        }
        for (final IJobProvidingService jobProvidingService : jobProvidingServices) {
            jobProvidingService.start();
        }
        LOGGER.info("Application started successfully");
    }

    public void shutdown() {
        LOGGER.info("Shutting down application...");
        for (final IJobExecutionService jobExecutionService : jobExecutionServices) {
            jobExecutionService.stop();
        }
        for (final IJobProvidingService jobProvidingService : jobProvidingServices) {
            jobProvidingService.stop();
        }
        LOGGER.info("Application was shut down successfully");
    }

    public List<IJobExecutionService> getJobExecutionServices() {
        return this.jobExecutionServices;
    }

    public void setJobExecutionServices(List<IJobExecutionService> jobExecutionServices) {
        this.jobExecutionServices = jobExecutionServices;
    }

    public List<IJobProvidingService> getJobProvidingServices() {
        return this.jobProvidingServices;
    }

    public void setJobProvidingServices(List<IJobProvidingService> jobProvidingServices) {
        this.jobProvidingServices = jobProvidingServices;
    }

    public synchronized static Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

}
