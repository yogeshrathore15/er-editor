package ru.soultakov.remotecontrol;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ru.soultakov.remotecontrol.core.impl.JobExecutionServiceImplTest;
import ru.soultakov.remotecontrol.core.impl.ant.AntTaskInvokerTest;
import ru.soultakov.remotecontrol.core.impl.ant.InvokeAntCommandTest;

@RunWith(Suite.class)
@SuiteClasses( 
        { AntTaskInvokerTest.class, 
          JobExecutionServiceImplTest.class,
          InvokeAntCommandTest.class })
public class AllTests {

}
