package ru.soultakov.remotecontrol.core.impl.commands;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import ru.soultakov.remotecontrol.ant.AntTaskInvokerTest;
import ru.soultakov.remotecontrol.core.ICommand;
import ru.soultakov.remotecontrol.core.exceptions.CommandExecutionException;

public class InvokeAntCommandTest {

    @Test
    public void testExecute() throws CommandExecutionException {
        final ICommand command = new InvokeAntCommand();
        final HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        hashMap.put("task", Arrays.asList(AntTaskInvokerTest.ECHO_TASK));
        final String result = command.execute(hashMap);
        assertEquals(result, AntTaskInvokerTest.EXPECTED_RESULT);
    }

}
