package ru.soultakov.remotecontrol;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.soultakov.remotecontrol.core.ICommandService;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class);

    private static ICommandService commandService;

    public static void main(String[] args) {
        final AbstractApplicationContext ac = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        ac.registerShutdownHook();
        commandService = (ICommandService) ac.getBean("commandService", ICommandService.class);
        commandService.start();
        LOGGER.info("Application started");
    }

    public static ICommandService getCommandService() {
        return commandService;
    }
}
