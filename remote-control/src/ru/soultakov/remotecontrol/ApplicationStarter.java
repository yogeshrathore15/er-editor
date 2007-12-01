package ru.soultakov.remotecontrol;

import org.apache.log4j.Logger;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationStarter {

    private static final Logger LOGGER = Logger.getLogger(ApplicationStarter.class);

    public static void main(String[] args) {
        LOGGER.info("Program started");
        final AbstractApplicationContext ac = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        LOGGER.info("Spring config read");
        ac.registerShutdownHook();
    }

}
