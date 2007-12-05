package ru.soultakov.remotecontrol.core.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;
import ru.soultakov.remotecontrol.core.exceptions.IllegalJobException;
import ru.soultakov.remotecontrol.core.exceptions.NoSuchCommandException;
import ru.soultakov.remotecontrol.core.impl.ant.AntTaskInvokerTest;
import ru.soultakov.remotecontrol.core.impl.ant.InvokeAntCommand;

public class JobExecutionServiceImplTest {

    private JobExecutionServiceImpl executor;

    @Before
    public void initExecutor() {
        final AbstractApplicationContext ac = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        executor = (JobExecutionServiceImpl) ac.getBean("jobExecutionService",
                JobExecutionServiceImpl.class);
    }

    @After
    public void stopExecutor() {
        executor.stop();
    }

    @Test
    public void testExecute() throws NoSuchCommandException, IllegalJobException,
            CommandExecutionException {
        final TestJob testJob = new TestJob("ant ", InvokeAntCommand.USAGE, false, "default");
        executor.execute(testJob);
        testJob.checkResult(0);
    }

    @Test
    public void testExecute2() throws NoSuchCommandException, IllegalJobException,
            CommandExecutionException {
        final TestJob testJob = new TestJob(AntTaskInvokerTest.ECHO_TASK,
                AntTaskInvokerTest.EXPECTED_RESULT, true, "ant");
        executor.execute(testJob);
        testJob.checkResult(0);
    }

}
