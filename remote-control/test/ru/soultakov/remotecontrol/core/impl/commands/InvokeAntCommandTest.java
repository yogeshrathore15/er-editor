package ru.soultakov.remotecontrol.core.impl.commands;

import static org.junit.Assert.assertEquals;

import java.util.*;

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

    @Test(expected = CommandExecutionException.class)
    public void testExecute2() throws CommandExecutionException {
        final ICommand command = new InvokeAntCommand();
        final String result = command.execute(Collections.singletonMap("task", Arrays
                .asList("this is not correct task xaxaxa")));
        assertEquals(result, AntTaskInvokerTest.EXPECTED_RESULT);
    }
}
