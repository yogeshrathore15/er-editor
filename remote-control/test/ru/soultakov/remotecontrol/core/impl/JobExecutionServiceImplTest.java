package ru.soultakov.remotecontrol.core.impl;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JobExecutionServiceImplTest {

    @Test
    public void testExecute() {
        final AbstractApplicationContext ac = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        final JobExecutionServiceImpl executor = (JobExecutionServiceImpl) ac.getBean(
                "jobExecutionService", JobExecutionServiceImpl.class);
        // executor.execute(new TestJob("ant " + ));
    }

}
