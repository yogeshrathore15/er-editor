package ru.soultakov.remotecontrol;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.soultakov.remotecontrol.core.ICommandService;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    private static ICommandService commandService;

    public static void main(String[] args) {
        final ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        commandService = (ICommandService) ac.getBean("commandService", ICommandService.class);
        commandService.start();
        LOGGER.info("Application started");
    }

    public static ICommandService getCommandService() {
        return commandService;
    }
}
